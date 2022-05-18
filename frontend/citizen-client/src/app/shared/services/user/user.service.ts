import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import User from '@core/interfaces/user'
import { AuthService } from '@services/auth/auth.service';
import { TokenStorageService } from '@services/token-storage/token-storage.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor( 
    private _http: HttpClient,
    private _authService: AuthService, 
    private _tokenStore: TokenStorageService ) { }

  create( user: User ): Observable<any> {
    const request = this._authService.prepareRequest( '/registration-user', 'json', false )
    return this._http.post( request.url, user, request.config )
  }

  getUserEmailFromToken(): string {
    const token =  this._tokenStore.retrieveToken()?.accessToken

    if( !token ) return null

    const payload64 = token.split( '.' )[1]
    const base64 = payload64.replace( /-/g, '+' )
                            .replace( /_/g, '/' )

    const jsonPayload = decodeURIComponent( 
                          atob( base64 )
                            .split( '' )
                              .map( 
                                character => 
                                  '%' + ( '00' + character.charCodeAt( 0 ).toString( 16 ) ).slice( -2 )  
                              )
                              .join( '' ) )

    return JSON.parse( jsonPayload )!.sub
  }

  getUserInfo(): Observable<any> {
    const request = this._authService.prepareRequest( '/user/info' )
    return this._http.get( request.url, request.config )
  }
}