import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Department from '@core/interfaces/department';
import { AuthService } from '@services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(
    private _http: HttpClient, 
    private _authService: AuthService ) { }

  list() {
    const request = this._authService.prepareRequest( '/manager/department/list' )
    return this._http.get( request.url, request.config )
  }

  createOrEdit( department: Department ) {
    const request = this._authService.prepareRequest( '/manager/department/edition' )
    return this._http.post( request.url, department, request.config )
  }
}