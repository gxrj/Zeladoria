import { 
  HttpErrorResponse, 
  HttpEvent, 
  HttpHandler, 
  HttpInterceptor, 
  HttpRequest 
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, finalize, switchMap, take } from 'rxjs/operators';
import { AuthService } from '@app/shared/services/auth/auth.service';
import { ToastService } from '@app/shared/services/toast/toast.service';
import { TokenStorageService } from '@app/shared/services/token-storage/token-storage.service';



@Injectable( { providedIn: 'root' } )
export class AuthInterceptor implements HttpInterceptor {

  private refreshingToken = false
  private refreshSubject = new BehaviorSubject( null )
  constructor( 
      private _toast: ToastService,
      private _authService: AuthService,
      private _tokenService: TokenStorageService ) {}

  intercept( req: HttpRequest<any>, next: HttpHandler ): Observable<HttpEvent<any>> {

    return next.handle( req ).pipe(
                catchError(
                    ( error: HttpErrorResponse ) => {
                        if( error && ( error.status === 401 || error.status === 0 ) ) {
                            if( this.refreshingToken ) {
                                return this.refreshSubject
                                            .pipe( 
                                                filter( result  => result ), 
                                                take(1),
                                                switchMap( () => next.handle( req ) ) )
                            }
                            else {
                                this.refreshingToken = true
                                this.refreshSubject.next( null )

                                return this._authService.refreshToken()
                                            .pipe(
                                                switchMap( token => {
                                                    this.refreshSubject.next( token )
                                                    this._tokenService.saveToken( token )
                                                    return next.handle( this.addRefreshToken( req ) )
                                                } ),
                                                finalize( () => this.refreshingToken = false )
                                            )
                            }
                        }
                        else
                            this._toast.displayMessage( 'Falha na interceptação' )
                        return throwError( error )
                    }
                )
    )
  }

  private addRefreshToken( req: HttpRequest<any> ) {

      const token = this._tokenService.retrieveToken()

      return req.clone( {
          setHeaders: {
              Authorization: `Bearer ${ token.accessToken }`
          }
      } )
  }
}