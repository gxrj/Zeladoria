import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

  async getToken( code: string ) {

    const url = this.authzServer.TOKEN_ENPOINT 
    
    const bodyParams = { 
      code: code,
      ...OAUTH2_CLIENT_CONFIG.TOKEN_ENDPOINT_PARAMS
    }

    const contentType = new HttpHeaders( { 'Content-Type': 'application/json;charset=UTF-8' } )
  
    this._http.post( url, bodyParams, { headers: contentType, observe: 'response', responseType: 'json' }  )
              .subscribe( {
                next: response => {

                        console.log( response )
                        
                        this.setToken( response ) 
                      },
                error: error => console.log( error )
              } )
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
    
    //localStorage.setItem( 'token', JSON.stringify( credentials ) )
  }
}