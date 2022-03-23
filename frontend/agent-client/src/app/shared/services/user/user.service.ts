import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '@services/auth/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor( private _http: HttpClient, 
              private _authService: AuthService ) { }

  getInfo( path: string ): Observable<any> {
    const request = this._authService.prepareRequest( path )

    return this._http.get( request.url, { headers:request.config.headers } )
  }
}