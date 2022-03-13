import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import Call from '@core/interfaces/call';
import Duty from '@core/interfaces/duty';

import { CallService } from '@services/call/call.service';
import { UserService } from '@services/user/user.service';

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
               private _callService: CallService ) {

      this.form = this._fb.group( {
        service: [ '' ,Validators.required ],
        zipCode: [ '' ],
        district: [ '' ],
        street: [ '' ],
        images: [ '' ],
        description: [ '' ]
      } )
  }

  ngOnInit() {
    this.form.patchValue( { service: this.selectedDuty?.description }  )
  }

  sendData() {

    this.prepareCall()
  }

  private prepareCall() {

    let email = this._userService.getUserEmailFromToken()
    if( email == undefined || !email ) email = "anonimo@fiscaliza.com"

    this.call = { ...this.form.value }
    this.call.address = { ...this.form.value }
    this.call.author = { username: email }
    this.call.status = "Em andamento"

    this._callService.create( this.call )
        .subscribe( {
          next: request => console.log(request),
          error: err => console.log(err)
        } )

  }
}