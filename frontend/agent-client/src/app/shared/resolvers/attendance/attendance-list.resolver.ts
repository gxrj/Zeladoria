import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { EMPTY, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AttendanceService } from '@services/attendance/attendance.service';
import { ToastService } from '@services/toast/toast.service';

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

      return this._attendanceService
                  .listAttendanceByFilter( filter, user )
                    .pipe(
                      catchError( error => {
                          console.log( error )
                          if( error.status )
                            this.errorRedirection()

                          return throwError( error )
                    } ) )
    }
  }

  private errorRedirection() {
    this._toast.displayMessage( 'Falha no carregamento de atendimentos' )
    this._router.navigateByUrl( '/home' )
  }
}