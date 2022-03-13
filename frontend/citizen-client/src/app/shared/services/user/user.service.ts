import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import User from '@core/interfaces/user'
import { AuthService } from '@services/auth/auth.service';
import { TokenStorageService } from '@services/token-storage/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor( 
    private _http: HttpClient,
    private _authService: AuthService, 
    private _tokenStore: TokenStorageService ) { }

  create( user: User ) {

    const request = this._authService.prepareRequest( '/registration-user' )
    
    return this._http.post( request.url, user, request.headers )
  }

  getUserEmailFromToken(): string {
    return this._tokenStore.retrieveToken()?.scope
  }

  getUserInfo() {
    const request = this._authService.prepareRequest( '/user/info' )

    let result = null

    this._http.get( request.url, request.headers )
              .subscribe( {
                next: response => result = response,
                error: error => result = error 
              } )

    console.log( 'result: ' + result )
  }
}