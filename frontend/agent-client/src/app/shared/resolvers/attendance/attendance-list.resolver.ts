import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import User from '@core/interfaces/user';
import { AttendanceService } from '@services/attendance/attendance.service';
import { ToastService } from '@services/toast/toast.service';
import { EMPTY, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AttendanceListResolver implements Resolve<any> {

  constructor(
    private _router: Router,
    private _toast: ToastService,
    private _attendanceService: AttendanceService ) { }

  resolve(): Observable<any> {
    const plainUser =  sessionStorage.getItem( 'user' )

    if( !plainUser ) {
      console.log( 'no user' )
      this.errorRedirection()
      return EMPTY
    }
    else {
      const user = JSON.parse( plainUser )
      const filter = this._attendanceService.getListFilter()

      if( filter === 'agent' )
        return this.getAttendanceListByAgent( user )
      else
        return this.getAttendanceListByDepartment( user )
    }
  }

  private getAttendanceListByDepartment( user: User ): Observable<any> {

    return this._attendanceService
                    .listByDepartment( user.department )
                      .pipe(
                        catchError( error => {
                            console.log( error )
                            if( error.status )
                              this.errorRedirection()

                            return throwError( error )
                      } ) )
  }

  private getAttendanceListByAgent( user : User ): Observable<any> {

     return this._attendanceService
                  .listByAgent( user )
                    .pipe(
                      catchError( error => {
                          console.log( error )
                          if( error.status )
                            this.errorRedirection()

                          return throwError( error )
                    } ) )
  }

  private errorRedirection() {
    this._toast.displayMessage( 'Falha no carregamento de ocorrências' )
    this._router.navigateByUrl( '/home' )
  }
}