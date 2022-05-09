import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

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
}