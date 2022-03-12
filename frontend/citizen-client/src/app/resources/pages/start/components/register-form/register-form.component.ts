import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { UserService } from '@app/shared/services/user/user.service';

@Component({
  selector: 'register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent implements OnInit {

  form: FormGroup

  constructor( 
    private _fb: FormBuilder, 
    private _userService: UserService ) {

    this.form = this._fb.group( {
      name: [ '', Validators.required ],
      username: [ '', Validators.required ],
      password: [ '', Validators.required ]
    } )
  }

  ngOnInit(): void {
  }

  register() {
    this._userService.create( this.form.value )
              .subscribe( { 
                next: response => console.log( response ),
                error: error => console.log( error ) 
              } )
  }
}
