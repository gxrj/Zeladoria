import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { EMPTY, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';

@Injectable({
  providedIn: 'root'
})
export class CallListResolver implements Resolve<any>{

  constructor( 
    private _router: Router,
    private _toast: ToastService, 
    private _callService: CallService ) { }

  resolve(): Observable<any> {

    const plainUser =  sessionStorage.getItem( 'user' )

    if( !plainUser ) {
      console.log( 'no user' )
      this.errorRedirection()
      return EMPTY
    }
    else {
        const status = this._callService.getSelectedCallStatus()
        return this._callService.list( JSON.parse( plainUser ), status )
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
    this._toast.displayMessage( 'Falha no carregamento de ocorrências' )
    this._router.navigateByUrl( '/home' )
  }
}