import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Category from '@core/interfaces/category';
import { AuthService } from '@services/auth/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private _http: HttpClient, 
    private _authService: AuthService ) { }

  getCategories(): Observable<any> {
    const request = this._authService.prepareRequest( '/manager/category/list' )
    return this._http.get( request.url, request.config )
  }

  createOrEdit( category: Category ): Observable<any> {
    const request = this._authService.prepareRequest( '/manager/category/list' )
    return this._http.post( request.url, category, request.config )
  }
}