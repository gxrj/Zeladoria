import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AuthService } from '@services/auth/auth.service';
import Call from '@core/interfaces/call';
import User from '@core/interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class CallService {

  constructor( 
    private _http: HttpClient, 
    private _authService: AuthService ) { }

  list( path: string, user: User ): Observable<any> {
    const request = this._authService.prepareRequest( path )

    return this._http.post( request.url, user, request.config )
  }
  
  update( path: string, call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( path )
    return this._http.post( request.url, call, request.config )
  }

  delete( path: string, call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( path )
    return this._http.post( request.url, call, request.config )
  }
}