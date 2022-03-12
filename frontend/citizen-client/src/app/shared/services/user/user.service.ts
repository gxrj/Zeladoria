import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import REQUEST from '@app/shared/globals/request.config';
import User from '@core/interfaces/user'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private resourceServer = REQUEST.resourceServer

  constructor( private _http: HttpClient ) { }

  create( user: User ) {

    const url = this.resourceServer.baseUrl + '/registration-user'
    const contentType = REQUEST.HEADER.JSON_CONTENT_TYPE

    return this._http.post( url, user, { headers: contentType } )
  }
}