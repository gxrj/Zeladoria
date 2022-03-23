import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { ToastService } from '@services/toast/toast.service';
import { EMPTY } from 'rxjs'
import { catchError } from 'rxjs/operators'

import { UserService } from '@services/user/user.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserInfoResolver implements Resolve<any>{

  constructor( 
    private _router: Router,
    private _toast: ToastService,
    private _userService: UserService, ) { }

  resolve(): Observable<any> {
    
      return this._userService.getInfo( '/agent/info' )
                  .pipe( 
                    catchError( () => {
                        this._toast.displayMessage( 'Falha no carregamento' )
                        this._router.navigateByUrl( '/home' )
                        return EMPTY
                    } ) )
  }
}