import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import OAUTH_REQUEST from '@app/oauth-request.config';
import { AuthService } from '@app/shared/services/auth/auth.service';
import { ToastService } from '@app/shared/services/toast/toast.service';
import { MenuController, PopoverController } from '@ionic/angular';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  isAuthenticated: boolean
  paths = [
    { url: '/home', label: 'Criar ocorrência' },
    { url: '/history', label: 'Minhas ocorrências' },
  ]

  constructor( 
    private _router: Router,
    private _toast: ToastService,
    private _menuCtrl: MenuController,
    private _authService: AuthService,
    private _popoverCtrl: PopoverController ) { }

  ngOnInit() {
    const token = sessionStorage.getItem( 'token' )
    this.isAuthenticated = token != null
  }

  closeMenu(){
    this._menuCtrl.close()
  }

  jumpToUserForm(){
    this._router.navigateByUrl( '/user-form' )
    this._popoverCtrl.dismiss()
  }

  logout() {
    const logoutUrl = OAUTH_REQUEST.authzServer.baseUrl + 'logout'

    this._popoverCtrl.dismiss()
    this._authService.revokeToken()
              .subscribe( 
                () => this._toast.displayMessage( 'Encerrando a sessão' ), 
                error => this._toast.displayMessage( error ) )
    sessionStorage.clear()
    setTimeout( () => window.location.href = logoutUrl, 1000 )
  }
}