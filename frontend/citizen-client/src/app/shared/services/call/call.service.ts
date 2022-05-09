import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CallService {

  constructor( private _http: HttpClient, 
               private _authService: AuthService ) { }

  create( form: any ): Observable<any> {
    const request = this._authService.prepareRequest( '/anonymous/calls/new', 'multipart', false )
    
    return this._http.post( request.url, form, { headers:request.config.headers } )
  }

  listByCitizen(): Observable<any> {
    const request = this._authService.prepareRequest( '/user/calls/all' )

    return this._http.get( request.url, request.config )
  }
}