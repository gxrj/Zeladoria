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

  create( path: string, attendance: Attendance ) {
    const request = this._authService.prepareRequest( path )
    return this._http.post( request.url, attendance, request.config )
  }

  list( path: string, call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( path )
    return this._http.post( request.url, call, request.config )
  }
}