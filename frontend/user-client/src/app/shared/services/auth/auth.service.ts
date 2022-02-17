import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import OAUTH2_CLIENT_CONFIG from './auth-service.config'
import REQUEST from '@globals/request.config'

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authzServer = REQUEST.API.AUTHZ_SERVER_URL

  constructor( private _http: HttpClient ) { }

  redirectToLoginPage() {

    let params = this.getUrlEndpointParams( OAUTH2_CLIENT_CONFIG.AUTHORIZE_ENDPOINT_PARAMS )

    const authzEndpoint = `${ this.authzServer.AUTHORIZE_ENPOINT }?${ params.toString() }`

    window.location.href = authzEndpoint
  }

  getUrlEndpointParams( endpointParameters: Object ) {
    
    const urlEndpointParams = new URLSearchParams()
    
    Object
      .entries( endpointParameters )
        .forEach( 
          entry => urlEndpointParams.append( entry[0], entry[1] ) 
        )
    
    return urlEndpointParams
  }

  getToken( code: string ) {

    const url = this.authzServer.TOKEN_ENPOINT 
    
    const params = { 
      code: code,
      ...OAUTH2_CLIENT_CONFIG.TOKEN_ENDPOINT_PARAMS
    }
   
    const body = this.getUrlEndpointParams( params ).toString()
    
    const contentType = REQUEST.HEADER.FORM_CONTENT_TYPE
  
    return this._http
                  .post( url, body, { 
                                      headers: contentType, 
                                      observe: 'response', 
                                      responseType: 'json' }  )
  }

  saveToken( responseData: any ) {

    if( !responseData ) return
    
    const credentials = {
              accessToken: responseData.access_token,
              refreshToken: responseData.refresh_token,
              expiration: new Date().getTime() + ( responseData.expires_in * 1000 ),
              scope: responseData.scope,
              token_type: responseData.token_type
          }
    
    sessionStorage.setItem( 'token', JSON.stringify( credentials ) )
  }
}