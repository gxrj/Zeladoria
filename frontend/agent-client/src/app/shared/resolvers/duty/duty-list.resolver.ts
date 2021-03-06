import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { DutyService } from '@services/duty/duty.service';
import { ToastService } from '@services/toast/toast.service';

@Injectable({
  providedIn: 'root'
})
export class DutyListResolver implements Resolve<any> {

  constructor( 
    private _router: Router,
    private _toast: ToastService,
    private _dutyService: DutyService ) { }

    resolve(): Observable<any> {

        return this._dutyService.loadDuties()
                    .pipe(
                      catchError( error => { 
                          this.errorRedirection()
                          return throwError( error )
                       } ) )
    }
  
    private errorRedirection() {
      this._toast.displayMessage( 'Falha no carregamento de serviços' )
      this._router.navigateByUrl( '/home' )
    }
}