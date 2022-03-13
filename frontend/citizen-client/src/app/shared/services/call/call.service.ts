import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Call from '@app/core/interfaces/call';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CallService {

  constructor( private _http: HttpClient, 
               private _authService: AuthService ) { }

  create( call: Call ) {
    const request = this._authService.prepareRequest( '/anonymous/calls' )
    return this._http.post( request.url, call, request.headers )
  }
}