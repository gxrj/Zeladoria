import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class DistrictService {

  constructor( private _http: HttpClient,
               private _authService: AuthService ) { }

  loadDistricts( path: string ) {
    const request = this._authService.prepareRequest( path, 'json', false )

    return this._http.get( request.url, { headers: request.config.headers } )
  }

}
