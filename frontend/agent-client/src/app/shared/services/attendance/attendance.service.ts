import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';
import User from '@core/interfaces/user';
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

  listByCall( call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( '/authenticated/attendance/by_call' )
    return this._http.post( request.url, call, request.config )
  }

  listByDepartment( deptName: string ) {
    const request = this._authService.prepareRequest( '/agent/attendance/by_department' )
    return this._http.post( request.url, { name: deptName }, request.config )
  }

  listByAgent( user: User ) {
    const request = this._authService.prepareRequest( '/agent/attendance/by_agent' )
    return this._http.post( request.url, user, request.config )
  }

  listAttendanceByFilter( filter: string, user: User ): Observable<any> {
    if( filter === 'agent' )
      return this.listByAgent( user )
    else
      return this.listByDepartment( user.department )
  }

  setListFilter( filter: string ) {
    sessionStorage.setItem( 'attendance-list-filter', filter )
  }

  getListFilter(): string {
    return sessionStorage.getItem( 'attendance-list-filter' )
  }

  listAttendanceByInterval( start: string, end:string, user: User ): Observable<any> {
    const path = this.buildIntervalQuery( '/manager/attendances/by_interval', start, end )
    const request = this._authService.prepareRequest( path )

    return this._http.post( request.url, user, request.config )
  }

  private buildIntervalQuery( path: string, start: string, end: string ): string {
    return `${path}?start=${start}&end=${end}`
  }
}