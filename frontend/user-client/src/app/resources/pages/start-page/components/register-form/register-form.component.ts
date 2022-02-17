import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {

  form: FormGroup

  constructor( fb: FormBuilder ) {
    this.form = fb.group( {
      name: [ '', Validators.required ],
      username: [ '', Validators.required ],
      password: [ '', Validators.required ]
    } )
  }

  ngOnInit(): void {
  }

  register() {
    alert( JSON.stringify( this.form.value ) )
  }
}