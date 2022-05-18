import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { ToastService } from '@app/shared/services/toast/toast.service';
import { UserService } from '@app/shared/services/user/user.service';
import { EMPTY, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserResolver implements Resolve<any> {

  constructor(
    private _router: Router,
    private _toast: ToastService,
    private _userService: UserService ) { }

  resolve(): Observable<any> {

    return this._userService
                  .getUserInfo()
                    .pipe(
                      catchError( () => {
                          this._toast.displayMessage( 'Falha no carregamento dos dados da conta' )
                          this._router.navigateByUrl( '/home' )
                          return EMPTY
                      } ) )
  }
}