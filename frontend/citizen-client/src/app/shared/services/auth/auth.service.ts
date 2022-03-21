import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import OAUTH2_CLIENT_CONFIG from './auth-service.config'
import REQUEST from '@globals/request.config'
import Token from '@app/core/interfaces/token';
import { TokenStorageService } from '../token-storage/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authzServer = REQUEST.authzServer
  private resourceServer = REQUEST.resourceServer

  constructor( private _http: HttpClient, 
               private _tokenService: TokenStorageService ) { }

  redirectToLoginPage() {

    let params = this.getUrlEndpointParams( OAUTH2_CLIENT_CONFIG.AUTHORIZE_ENDPOINT_PARAMS )
    let authorizeUrl = this.authzServer.baseUrl + this.authzServer.authorizeEndpont

    const authzEndpoint = `${ authorizeUrl }?${ params.toString() }`

    window.location.href = authzEndpoint
  }

  private getUrlEndpointParams( endpointParameters: Object ) {
    
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
      ...OAUTH2_CLIENT_CONFIG.TOKEN_ENDPOINT_PARAMS
    }
   
    const body = this.getUrlEndpointParams( params ).toString()
    
    const contentType = REQUEST.HEADER.FORM_CONTENT_TYPE
  
    return this._http
                  .post( tokenEndpointUrl, body, { 
                                                    headers: contentType, 
                                                    observe: 'response', 
                                                    responseType: 'json' }  )
  }

  refreshToken() {
    const request = this.prepareRequest( '/oauth2/token', 'form-encoded' )
   
    let body = this.setRefreshTokenFormEncoded( 'refresh_token' )

    this._http.post( request.url, body, request.config )
  }

  prepareRequest( path: string, contentFormat: string = 'json', setAuthentication: boolean = true ) {
    const token: Token = this._tokenService.retrieveToken()
    if( !token ) setAuthentication = false

    if( contentFormat != 'json' && contentFormat != 'form-encoded' ) 
      throw new TypeError( 'contentFormat must be json or form-encoded ' )

    let contentType: string = null
    if( contentFormat === 'json') contentType = 'application/json'
    if( contentFormat === 'form-encoded' ) contentType = 'application/x-www-form-urlencoded' 
   
    const url = this.resourceServer.baseUrl + path
    
    let header: any
    if( setAuthentication )
      header = this.authorizedHeader( token, contentType )
    else
      header = this.anonymousHeader( contentType )

    const config = { headers: header }

    return { url, config }
  }

  private setRefreshTokenFormEncoded( tokenType: string = 'refresh_token' ): FormData  {
    
    if( tokenType != 'refresh_token' && tokenType != 'access_token' ) 
       throw new TypeError( 'tokenType must be refresh_token or access_token ' )
    
    let form = new FormData()
    const token = this._tokenService.retrieveToken()
    const clientParams = OAUTH2_CLIENT_CONFIG.TOKEN_ENDPOINT_PARAMS
    
    form.append( 'grant_type', tokenType )
    form.append( tokenType, token.refreshToken )
    form.append( 'client_id', clientParams.client_id )
    form.append( 'client_secret', clientParams.client_secret.toString() )

    return form
  }

  authorizedHeader( token: Token, content: string ) {
    return {
      'content-type': content,
      authorization: token?.tokenType +' '+ token?.accessToken 
    }
  }
  anonymousHeader( content: string ) {
    return {
      'content-type': content
    }
  }
}