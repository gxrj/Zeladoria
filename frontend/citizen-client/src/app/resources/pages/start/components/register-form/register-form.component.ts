import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastService } from '@app/shared/services/toast/toast.service';

import { UserService } from '@services/user/user.service';

@Component({
  selector: 'register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent implements OnInit {

  form: FormGroup

  constructor( 
    private _fb: FormBuilder, 
    private _userService: UserService,
    private _toastService: ToastService ) {

    this.form = this._fb.group( {
      name: [ '', Validators.required ],
      email: [ '', Validators.required ],
      password: [ '', Validators.required ]
    } )
  }

  ngOnInit(): void {
  }

  register() {
    this._userService.create( this.form.value )
              .subscribe( 
                resp => this._toastService.displayMessage( resp?.message ),
                ( err: HttpErrorResponse ) => this._toastService.displayMessage( JSON.stringify( err?.message ) )
              )
  }
}
