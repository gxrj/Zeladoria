import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import Call from '@app/core/interfaces/call';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor( 
    private _http: HttpClient, 
    private _authService: AuthService ) { }

  loadAllCallImgFiles( call: Call ): Observable<any> {
    const request = this._authService.prepareRequest( '/authenticated/call/files/zip' )

    return this._http.post( request.url, call, { responseType: 'blob', ...request.config } )
  }
}
