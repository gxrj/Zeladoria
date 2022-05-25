import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';

import { ToastService } from '@services/toast/toast.service';
import { UserService } from '@services/user/user.service';

@Injectable({
  providedIn: 'root'
})
export class UserListResolver implements Resolve<any> {

  constructor(
    private _router: Router,
    private _toast: ToastService,
    private _userService: UserService ) { }

  resolve(): Observable<any> {
    return this._userService
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
    this._toast.displayMessage( 'Falha no carregamento de usuários' )
    this._router.navigateByUrl( '/home' )
  }
}