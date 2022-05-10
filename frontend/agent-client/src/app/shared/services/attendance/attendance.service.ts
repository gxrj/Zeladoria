import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';
import { AuthService } from '@services/auth/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  constructor( 
    private _http: HttpClient,
    private _authService: AuthService ) { }

  create( attendance: Attendance ) {
    const request = this._authService.prepareRequest( '/agent/attendance/new' )
    return this._http.post( request.url, attendance, request.config )
  }

  list( call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( '/authenticated/attendance/by_call' )
    return this._http.post( request.url, call, request.config )
  }
}