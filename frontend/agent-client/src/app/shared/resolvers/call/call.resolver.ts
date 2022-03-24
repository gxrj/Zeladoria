import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';

import User from '@core/interfaces/user';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import { EMPTY, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CallResolver implements Resolve<any>{

  constructor( private _router: Router,
              private _toast: ToastService, 
              private _callService: CallService ) { }

  resolve(): Observable<any> {

    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )

    if( !user ) 
      return this.errorRedirection()
    else
      return this._callService.list( '/agent/calls/all', user )
                  .pipe(
                    catchError( () => this.errorRedirection() )
                  )
  }

  private errorRedirection(): Observable<never> {
    this._toast.displayMessage( 'Falha no carregamento' )
    this._router.navigateByUrl( '/home' )
    return EMPTY 
  }
}