import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { CallService } from '@app/shared/services/call/call.service';
import { ToastService } from '@app/shared/services/toast/toast.service';
import { EMPTY, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CallHistoryResolver implements Resolve<any> {

  constructor( 
    private _router: Router,
    private _toast: ToastService,
    private _callService: CallService ) { }

  resolve(): Observable<any> {

    return this._callService
                .listByCitizen().pipe(
                  catchError( () => { 
                    this._toast.displayMessage( 'Falha no carregamento do histórico' )
                    this._router.navigateByUrl( '/' )
                    return EMPTY 
                  } )
                )
  }
}
