import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Call from '@app/core/interfaces/call';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CallService {

  constructor( private _http: HttpClient, 
               private _authService: AuthService ) { }

  create( call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( '/anonymous/calls/new', 'json', false )
    
    return this._http.post( request.url, call, { headers:request.config.headers } )
  }
}