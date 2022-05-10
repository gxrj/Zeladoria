import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Attendance } from '@app/core/interfaces/attendance';

import Call from '@app/core/interfaces/call';

import { AuthService } from '@shared/services/auth/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  constructor(
    private _http: HttpClient,
    private _authService: AuthService ) { }

  list( call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( '/authenticated/attendance/by_call' )
    return this._http.post( request.url, call, request.config )
  }

  update( attendance: Attendance ): Observable<any> {
    const request = this._authService.prepareRequest( '/authenticated/attendance/edition' )
    return this._http.post( request.url, attendance, request.config )
  }
}