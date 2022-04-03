import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '@services/auth/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DutyService {

  constructor( 
    private _http: HttpClient,
    private _authSerivce: AuthService ) { }

  loadDuties( path: string ): Observable<any> {
    const request = this._authSerivce.prepareRequest( path, 'json', false )
    return this._http.get( request.url, request.config )
  }
}