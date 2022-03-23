import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '@services/auth/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CallService {

  constructor( private _http: HttpClient, 
              private _authService: AuthService ) { }

  list( call: any ): Observable<any> {
    const request = this._authService.prepareRequest( '/agent/calls/all' )

    return this._http.post( request.url, call, { headers:request.config.headers } )
  }
}