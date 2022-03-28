import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Attendance } from '@core/interfaces/attendance';
import { AuthService } from '@services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  constructor( 
    private _http: HttpClient,
    private _authService: AuthService ) { }

  create( path: string, attendance: Attendance ) {
    const request = this._authService.prepareRequest( path )
    return this._http.post( request.url, attendance, { headers: request.config.headers } )
  }
}