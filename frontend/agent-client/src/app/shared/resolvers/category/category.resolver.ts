import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { CategoryService } from '@services/category/category.service';
import { ToastService } from '@services/toast/toast.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryResolver implements Resolve<any> {

  constructor(
    private _router: Router,
    private _toast: ToastService,
    private _categoryService: CategoryService ) { }

  resolve(): Observable<any> {
    return this._categoryService
                .getCategories()
                .pipe(
                  catchError( error => {
                      console.log( error )
                      if( error.status )
                        this.errorRedirection()

                      return throwError( error )
                } ) )
  }

  private errorRedirection() {
    this._toast.displayMessage( 'Falha no carregamento de categorias' )
    this._router.navigateByUrl( '/home' )
  }
}