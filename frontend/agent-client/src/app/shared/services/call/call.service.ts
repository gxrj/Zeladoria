import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Call from '@core/interfaces/call';
import User from '@core/interfaces/user';
import { AuthService } from '@services/auth/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CallService {

  constructor( 
    private _http: HttpClient, 
    private _authService: AuthService ) { }

  list( path: string, user: User ): Observable<any> {
    const request = this._authService.prepareRequest( path )

    return this._http.post( request.url, user, { headers:request.config.headers } )
  }
  
  update( path: string, call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( path, 'json', false )

    return this._http.post( request.url, call, { headers:request.config.headers } )
  }
}