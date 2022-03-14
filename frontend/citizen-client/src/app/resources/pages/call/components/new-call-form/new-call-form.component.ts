import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import Call from '@core/interfaces/call';
import Duty from '@core/interfaces/duty';

import { CallService } from '@services/call/call.service';
import { UserService } from '@services/user/user.service';
import { ToastController } from '@ionic/angular';


@Component({
  selector: 'new-call-form',
  templateUrl: './new-call-form.component.html',
  styleUrls: ['./new-call-form.component.scss'],
})
export class NewCallFormComponent implements OnInit {

  @Input() selectedDuty: Duty | null

  form: FormGroup
  call: Call

  constructor( private _fb: FormBuilder,
               private _userService: UserService,
               private _callService: CallService,
               public toastController: ToastController ) {

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
    this.form.patchValue( { dutyDescription: this.selectedDuty?.description }  )
  }

  sendData(): void {

    this.prepareCall()

    this._callService.create( this.call )
                    .subscribe( 
                      res => this.displayMessage( res.message ),
                      error => console.log( error )
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

  async displayMessage( message: string ) {
    const toast = await this.toastController.create( {

        message: message,
        duration: 2000,
        position: 'top'
    } )

    toast.present()
  }
}