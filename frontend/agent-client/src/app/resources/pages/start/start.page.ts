import { Component, OnInit } from '@angular/core';

import { AuthService } from '@services/auth/auth.service';

@Component({
  selector: 'app-start',
  templateUrl: './start.page.html',
  styleUrls: ['./start.page.scss'],
})
export class StartPage implements OnInit {

  constructor( private _authService: AuthService ) { }

  ngOnInit() {

    setTimeout( () => this.authenticate(), 2000 )
  }

  authenticate() {
    this._authService.redirectToLoginPage()
  }
}
