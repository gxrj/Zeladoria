import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import Call from '@core/interfaces/call';
import Duty from '@core/interfaces/duty';

import { CallService } from '@services/call/call.service';
import { UserService } from '@services/user/user.service';
import { ToastService } from '@app/shared/services/toast/toast.service';
import { Router } from '@angular/router';


@Component({
  selector: 'new-call-form',
  templateUrl: './new-call-form.component.html',
  styleUrls: ['./new-call-form.component.scss'],
})
export class NewCallFormComponent implements OnInit {

  @Input() selectedDuty: Duty | null
  @Input() districtList: any

  form: FormGroup
  call: Call

  constructor( private _fb: FormBuilder,
               private _router: Router,
               private _userService: UserService,
               private _callService: CallService,
               public _toastService: ToastService ) {

      this.form = this._fb.group( {
        dutyDescription:  [ '' ,Validators.required ],
        zipCode: [ '' ],
        district: [ '' ],
        street: [ '' ],
        images: [ '' ],
        description: [ '' ]
      } )
  }

  ngOnInit() {
    const duty = sessionStorage.getItem( 'duty-description' )

    if( !duty ) {
      this.form.patchValue( { dutyDescription: this.selectedDuty?.description }  )
      sessionStorage.setItem( 'duty-description', this.selectedDuty?.description )
    }
    else 
      this.form.patchValue( { dutyDescription: duty } )
  }

  sendData(): void {

    this.prepareCall()

    this._callService.create( this.call )
                    .subscribe( 
                      res => {
                        this._toastService.displayMessage( res?.message )
                        this._router.navigateByUrl( '/home' ) 
                      },
                      error => this._toastService.displayMessage( error?.message )
                    )
  }

  private prepareCall() {

    let email = this._userService.getUserEmailFromToken()

    if( email == undefined || !email ) email = "anonimo@fiscaliza.com"
     
    const values = { ...this.form.value }
    this.call = {
                  duty: {
                    description: this.selectedDuty.description,
                    department: this.selectedDuty.department
                  },
                  description: values.description,
                  images: values.images,
                  address: {
                    longitude: null,
                    latitude: null,
                    zip_code: values.zipCode,
                    public_place: values.street,
                    district: values.district,
                    reference: values.reference
                  },
                  author: { 
                    email: email 
                  },
                  status:"Em andamento"
                }
  }

  ngOnDestroy() {
    sessionStorage.removeItem( 'duty-description' )
  }
}