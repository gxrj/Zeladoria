import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Call from '@app/core/interfaces/call';
import Duty from '@app/core/interfaces/duty';
import { AuthService } from '@app/shared/services/auth/auth.service';
import { CallService } from '@app/shared/services/call/call.service';
import { TokenStorageService } from '@app/shared/services/token-storage/token-storage.service';
import { UserService } from '@app/shared/services/user/user.service';

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
               private _authService: AuthService,
               private _tokenStorage: TokenStorageService,
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
    console.log( this.form.valid )
  }

  sendData() {

    this.prepareCall()
  }

  private prepareCall() {

    let email = this._userService.getUserEmailFromToken()
    if( email == undefined || !email ) email = "anonimo@fiscaliza.com"
    this.call = { ...this.form.value }
    this.call.address =  { ...this.form.value }
    this.call.author = { username: email }
    this.call.status = "Em andamento"

    console.log( this.call )
  }
}