import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { EMPTY, Observable } from 'rxjs';

import { DistrictService } from '@services/district/district.service';
import { ToastService } from '@services/toast/toast.service';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DistrictResolver implements Resolve<any>{

  constructor( private _router: Router,
              private _toast: ToastService,
              private _districtService: DistrictService ) { }

    resolve(): Observable<any> {

      return  this._districtService
                    .loadDistricts( '/anonymous/district/all' )       
                      .pipe( 
                        catchError( () => {
                            this._toast.displayMessage( 'Falha no carregamento' )
                            this._router.navigateByUrl( '/home' )
                            return EMPTY
                        } ) )
    }
}
