import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import Token from '@app/core/interfaces/token';
import OAUTH_CLIENT_CONFIG from '@app/oauth-client.config';
import OAUTH_REQUEST from '@app/oauth-requests.config';
import { TokenStorageService } from '../token-storage/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authzServer = OAUTH_REQUEST.authzServer
  private resourceServer = OAUTH_REQUEST.resourceServer

  constructor( 
    private _http: HttpClient,
    private _tokenService: TokenStorageService ) { }

  redirectToLoginPage() {

    let params = this.convertToUrlEndpointParams( OAUTH_CLIENT_CONFIG.AUTHORIZE_ENDPOINT_PARAMS )
    let authorizeUrl = this.authzServer.baseUrl + this.authzServer.authorizeEndpont

    const authzEndpoint = `${ authorizeUrl }?${ params.toString() }`

    window.location.href = authzEndpoint
  }

  private convertToUrlEndpointParams( endpointParameters: Object ) {
    
    const urlEndpointParams = new URLSearchParams()
    
    Object
      .entries( endpointParameters )
        .forEach( 
          entry => urlEndpointParams.append( entry[0], entry[1] ) 
        )
    
    return urlEndpointParams
  }

  getToken( code: string ) {

    const tokenEndpointUrl = this.authzServer.baseUrl + this.authzServer.tokenEnpoint
    
    const params = { 
      code: code,
      ...OAUTH_CLIENT_CONFIG.TOKEN_ENDPOINT_PARAMS
    }
    
    const body = this.convertToUrlEndpointParams( params ).toString()
    
    const contentType = OAUTH_REQUEST.HEADER.FORM_CONTENT_TYPE
  
    return this._http
                  .post( tokenEndpointUrl, body, { 
                                                    headers: contentType, 
                                                    observe: 'response', 
                                                    responseType: 'json' }  )
  }

  refreshToken() {
    const request = this.prepareRequest( 'oauth2/token', 'form-encoded', true, 'auth' )
    
    let body = this.setRefreshTokenUrlParams( 'refresh_token' )

    return this._http.post( request.url, body, request.config )
  }

  prepareRequest( path: string, contentFormat: string = 'json', setAuthentication: boolean = true,
                  targetServer: string = 'resource' ) {

    const token: Token = this._tokenService.retrieveToken()
    if( !token ) setAuthentication = false

    let contentType: string = ''
    let url: string = ''

    switch( contentFormat ) {
      case 'form-encoded': 
        contentType = 'application/x-www-form-urlencoded' 
        break
      case 'multipart':
        contentType = 'multipart' 
        break
      case 'octet':
        contentType = 'application/octet-stream' 
        break
      default: 
        contentType = 'application/json'
        break
    }

    switch( targetServer ) {
      case 'auth': 
        url = this.authzServer.baseUrl
        break
      default: 
        url = this.resourceServer.baseUrl
        break
    }
   
    url += path
    
    let header = setAuthentication ? 
                this.buildHeader( contentType, token ) 
                : this.buildHeader( contentType, null )

    const config = { headers: header }

    return { url, config }
  }

  private setRefreshTokenUrlParams( tokenType: string = 'refresh_token' ): URLSearchParams {
    
    if( tokenType != 'refresh_token' && tokenType != 'access_token' ) 
        throw new TypeError( 'tokenType must be refresh_token or access_token ' )
    
    let params = new URLSearchParams()
    const token = this._tokenService.retrieveToken()
    const clientParams = OAUTH_CLIENT_CONFIG.TOKEN_ENDPOINT_PARAMS
    
    params.append( 'grant_type', tokenType )
    params.append( tokenType, token.refreshToken )
    params.append( 'client_id', clientParams.client_id )
    params.append( 'client_secret', clientParams.client_secret.toString() )

    return params
  }
  
  private buildHeader( content: string, token: Token ) {
    if( !token )
      return content === 'multipart' ? {} : { 'content-type': content }

    const bearer = token?.tokenType +' '+ token?.accessToken
   
    return content === 'multipart' ? 
      { authorization: bearer } : { authorization: bearer, 'content-type': content  }
  }
}