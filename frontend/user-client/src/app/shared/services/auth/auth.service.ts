import { HttpClient, HttpParams } from '@angular/common/http';
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
    
    const bodyParams = this.getHttpEndpointParams( OAUTH2_CLIENT_CONFIG.TOKEN_ENDPOINT_PARAMS )
    bodyParams.append( 'code', code )
    
    const options = {
      headers: REQUEST.HEADER.JSON_CONTENT_TYPE
    }

    const url = this.authzServer.TOKEN_ENPOINT

    this._http.post( url, bodyParams, options )
              .subscribe( response => console.log( response ) )
  }

  getHttpEndpointParams( endpointParameters: Object ) {

    const httpEndpointParams = new HttpParams()
    
    Object
      .entries( endpointParameters )
        .forEach( 
          entry => httpEndpointParams.append( entry[0], entry[1] ) 
        )
    
    return httpEndpointParams
  }

  setToken( responseData: any ) {

    if( !responseData ) return

    let credentials = {
      accessToken: responseData.access_token,
      refreshToken: responseData.refresh_token,
      expiration: new Date().getTime() + ( responseData.expires_in * 1000 )
    }
    
    localStorage.setItem( 'token', JSON.stringify( credentials ) )
  }
}