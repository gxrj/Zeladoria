import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { DutyService } from '@services/duty/duty.service';
import { ToastService } from '@services/toast/toast.service';
import { EMPTY, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DutyResolver implements Resolve<any> {

  constructor( 
    private _router: Router,
    private _toast: ToastService,
    private _dutyService: DutyService ) { }

    resolve(): Observable<any> {

        return this._dutyService.loadDuties( '/anonymous/duty/all' )
                    .pipe(
                      catchError( () => this.errorRedirection() )
                    )
    }
  
    private errorRedirection(): Observable<never> {
      this._toast.displayMessage( 'Falha no carregamento de serviços' )
      this._router.navigateByUrl( '/home' )
      return EMPTY 
    }
}