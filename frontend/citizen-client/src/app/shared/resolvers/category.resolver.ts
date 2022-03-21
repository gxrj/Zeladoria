import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { EMPTY, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators'

import { CategoryService } from '@services/category/category.service';
import { ToastService } from '../services/toast/toast.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryResolver implements Resolve<any> {

  constructor( private _router: Router,
              private _toast: ToastService,
              private _categoryService: CategoryService ) { }

  resolve(): Observable<any> {
    return this._categoryService.loadCategories( '/anonymous/duty/categories' )
                .pipe( catchError( () => { 
                  this._toast.displayMessage( 'Falha no carregamento' )
                  this._router.navigate( [ '/' ] )
                  return EMPTY
                 } ) )
  }
}
