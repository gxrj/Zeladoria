import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class DistrictService {

  constructor( private _http: HttpClient,
               private _authService: AuthService ) { }

  loadDistricts() {
    const request = this._authService.prepareRequest( '/anonymous/district/all', 'json', false )

    return this._http.get( request.url, { headers: request.config.headers } )
  }
}