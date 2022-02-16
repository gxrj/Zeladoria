import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@app/shared/services/auth/auth.service';

@Component({
  selector: 'login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  constructor( 
    private _router: Router, 
    private _authService: AuthService ) { }

  ngOnInit(): void {
  }

  login() {
    this._authService.redirectToLoginPage()
  }

  anonymousForward() {
    this._router.navigateByUrl( '/home' )
  }
}