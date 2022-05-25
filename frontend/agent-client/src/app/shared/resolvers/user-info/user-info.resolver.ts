import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { ToastService } from '@services/toast/toast.service';
import { throwError, Observable } from 'rxjs'
import { catchError } from 'rxjs/operators'

import { UserService } from '@services/user/user.service';

@Injectable({
  providedIn: 'root'
})
export class UserInfoResolver implements Resolve<any>{

  constructor( 
    private _router: Router,
    private _toast: ToastService,
    private _userService: UserService ) { }

  resolve(): Observable<any> {

      return this._userService.getInfo()
                  .pipe( 
                    catchError( error => {
                        console.log( error )
                        if( error.status ) {
                          this._toast.displayMessage( 'Falha no carregamento de dados do usuário' )
                          this._router.navigateByUrl( '/home' )
                        }
                        return throwError( error )
                    } ) )
  }
}