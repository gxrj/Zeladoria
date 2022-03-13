import { Injectable } from '@angular/core';
import Token from '@app/core/interfaces/token';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  saveToken( responseData: any ) {

    if( !responseData ) return
    
    const credentials: Token = {
              accessToken: responseData.access_token,
              refreshToken: responseData.refresh_token,
              expiration: new Date().getTime() + ( responseData.expires_in * 1000 ),
              scope: responseData.scope,
              tokenType: responseData.token_type
          }
    
    sessionStorage.setItem( 'token', JSON.stringify( credentials ) )
  }

  retrieveToken(): Token {
    return JSON.parse( sessionStorage.getItem( 'token' ) )
  }
}
