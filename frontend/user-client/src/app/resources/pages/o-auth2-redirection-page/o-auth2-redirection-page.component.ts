import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { AuthService } from '@app/shared/services/auth/auth.service';

@Component({
  selector: 'app-o-auth2-redirection-page',
  templateUrl: './o-auth2-redirection-page.component.html',
  styleUrls: ['./o-auth2-redirection-page.component.css']
})
export class OAuth2RedirectionPageComponent implements OnInit {

  constructor( 
    private _authService: AuthService,
    private _route: ActivatedRoute ) { }

  ngOnInit(): void {
    let code = this._route.snapshot.queryParams[ 'code' ]
    this._authService.getToken( code )

    console.log( localStorage.getItem( 'token' ) )
  }
}
