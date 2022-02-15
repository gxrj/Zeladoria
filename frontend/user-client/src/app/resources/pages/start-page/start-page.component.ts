import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'

import { AuthService } from '@app/shared/services/auth/auth.service';

@Component({
  selector: 'start-page',
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.css']
})
export class StartPageComponent implements OnInit {

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