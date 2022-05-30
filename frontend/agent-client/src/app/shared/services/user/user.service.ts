import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import User from '@core/interfaces/user';
import { AuthService } from '@services/auth/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor( 
    private _http: HttpClient, 
    private _authService: AuthService ) { }

  getInfo(): Observable<any> {
    const request = this._authService.prepareRequest( '/agent/info' )
    return this._http.get( request.url, request.config )
  }

  list(): Observable<any> {
    const request = this._authService.prepareRequest( '/manager/agent/list' )
    return this._http.get( request.url, request.config )
  }

  createOrUpdateUser( user: User ): Observable<any> {
    const request = this._authService.prepareRequest( '/manager/agent-edition' )
    return this._http.post( request.url, user, request.config )
  }

  updateAccount( user: User ): Observable<any> {
    const request = this._authService.prepareRequest( '/agent/account/edition' )
    return this._http.post( request.url, user, request.config )
  }
}