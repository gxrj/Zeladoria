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

  list( user: User, status: string ): Observable<any> {
    const path = this.buildStatusQuery( '/agent/calls/all', status )
    const request = this._authService.prepareRequest( path )

    return this._http.post( request.url, user, request.config )
  }

  private buildStatusQuery( path: string, status: string ): string {
    return status === '' ?  path : `${path}?status=${status}` 
  }
  
  update( call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( '/authenticated/call/edition' )
    return this._http.post( request.url, call, request.config )
  }

  delete( call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( '/agent/calls/deletion' )
    return this._http.post( request.url, call, request.config )
  }

  getSelectedCallStatus(): string{
    return sessionStorage.getItem( 'call-status' )
  }

  setSelectedCallStatus( status: string ) {
    sessionStorage.setItem( 'call-status', status )
  }
}