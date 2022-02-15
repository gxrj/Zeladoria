import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import OAUTH2_CLIENT_CONFIG from './auth-service.config'
import REQUEST from '@globals/request.config'
import { first, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor( private _http: HttpClient, private _router: Router ) { }

  redirectToLoginPage() {

    let params = this.getUrlEndPointParams( OAUTH2_CLIENT_CONFIG.AUTHORIZE_ENDPOINT_PARAMS )

    const authzEndpoint = `${ REQUEST.API.AUTHZ_SERVER_URL }?${ params.toString() }`

    window.location.href = authzEndpoint
  }

  getToken( code: string ) {
    
    const urlParams = this.getUrlEndPointParams( OAUTH2_CLIENT_CONFIG.TOKEN_ENDPOINT_PARAMS )
    urlParams.append( 'code', code )

    const options = {
      headers: REQUEST.HEADER.JSON_CONTENT_TYPE,
      params: urlParams
    }

    const url = REQUEST.API.AUTHZ_SERVER_URL

    this._http.post( url, options )
              .pipe( 
                  first(),
                  tap( 
                      {
                        next: response => this.setToken( response ),
                        error: err => { 
                                alert( `Bad credentials: ${ err }` )
                                this._router.navigateByUrl( 'start' ) 
                              }
                      } 
                  ) 
              )
  }

  getUrlEndPointParams( endpointParameters: Object ) {
    
    const urlEndpointParams = new URLSearchParams()
    
    Object
      .entries( endpointParameters )
        .forEach( 
          entry => urlEndpointParams.append( entry[0], entry[1] ) 
        )
    
    return urlEndpointParams
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