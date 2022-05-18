import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@app/shared/services/auth/auth.service';
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
    this._popoverCtrl.dismiss()
    this._authService.revokeToken().subscribe( res => console.log( res ) )
    sessionStorage.clear()
    this._router.navigateByUrl( '/' )
  }
}