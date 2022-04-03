import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import Call from '@core/interfaces/call';
import { AuthService } from '@services/auth/auth.service';


@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor( 
    private _http: HttpClient, 
    private _authService: AuthService ) { }

  loadAllCallImgFiles( path: string, call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( path )
    return this._http.post( request.url, call, { responseType: 'blob', 
                                                ...request.config } )
  }
}