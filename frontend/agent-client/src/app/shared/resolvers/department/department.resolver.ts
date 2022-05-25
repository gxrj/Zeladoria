import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { DepartmentService } from '@services/department/department.service';
import { ToastService } from '@services/toast/toast.service';

@Injectable({
  providedIn: 'root'
})
export class DepartmentResolver implements Resolve<any> {

  constructor(
    private _router: Router,
    private _toast: ToastService,
    private _deptService: DepartmentService ) { }

  resolve(): Observable<any> {
    return this._deptService
                .list()
                  .pipe(
                    catchError( error => {
                        console.log( error )
                        if( error.status )
                          this.errorRedirection()

                        return throwError( error )
                  } ) )
  }

  private errorRedirection() {
    this._toast.displayMessage( 'Falha no carregamento de secretarias' )
    this._router.navigateByUrl( '/home' )
  }
}