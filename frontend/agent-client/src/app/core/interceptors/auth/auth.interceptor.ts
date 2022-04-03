import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";

import { 
    HttpEvent, 
    HttpHandler,
    HttpRequest,  
    HttpInterceptor,
    HttpErrorResponse
} from "@angular/common/http";
import { Router } from "@angular/router";

import { AuthService } from "@services/auth/auth.service";
import { TokenStorageService } from "@services/token-storage/token-storage.service";
import { ToastService } from "@services/toast/toast.service";
import { catchError } from "rxjs/operators";


@Injectable( { providedIn: 'root' } )
export class AuthInterceptor implements HttpInterceptor {

    tokenRefreshed = false

    constructor(
        private _router: Router, 
        private _toast: ToastService,
        private _authService: AuthService,
        private _tokenService: TokenStorageService ) {}

    intercept( req: HttpRequest<any>, next: HttpHandler ): Observable<HttpEvent<any>> {
        const token = this._tokenService.retrieveToken()
        const isHttpError = ( error: any ) => error instanceof HttpErrorResponse

        return next.handle( req ).pipe( 
                catchError( 
                    error => { 
                        if( token && isHttpError( error ) && ( error.status === 401 || error.status === 0 ) ) {
                            this.refreshToken()
                            if( this.tokenRefreshed ) {
                                const token = this._tokenService.retrieveToken()
                                const newReq = req.clone( 
                                    { setHeaders:{ Authorization: `Bearer ${ token.accessToken }` } } )
                                this.tokenRefreshed = false
                                return next.handle( newReq )
                            }
                        }
                        console.log( 'Error intercepted' )
                        return throwError( error )
                    }
                ) )
    }

    private refreshToken() {
        this._authService.refreshToken()
            .subscribe(
                res => { 
                    this._tokenService.saveToken( res )
                    this.tokenRefreshed = true
                },
                error =>{ 
                    console.log( error )
                    this._toast.displayMessage( 'Falha de sessão, tentando reautenticar' )
                    this._router.navigateByUrl( 'start' )
                }
            )
    }
}