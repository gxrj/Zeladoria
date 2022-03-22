import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from '@services/auth/auth.service';
import { TokenStorageService } from '@services/token-storage/token-storage.service';

@Component( {
  selector: 'oauth-redirect',
  templateUrl: './oauth-redirect.page.html',
  styleUrls: ['./oauth-redirect.page.scss'],
} )
export class OauthRedirectPage implements OnInit {

  constructor(
    private _authService: AuthService,
    private _route: ActivatedRoute,
    private _router: Router,
    private _tokenStorage: TokenStorageService ) { }

  ngOnInit(): void {
    let code = this._route.snapshot.queryParams[ 'code' ]

    this._authService.getToken( code )              
                        .subscribe( {
                          next: response => {
                                  this._tokenStorage.saveToken( response.body ) 
                                },
                          error: error => console.log( error )
                        } )
    setTimeout( () => this._router.navigateByUrl( 'home' ), 1500 )
  }

}