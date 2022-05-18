import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
    private _popoverCtrl: PopoverController ) { }

  ngOnInit() {
    const token = sessionStorage.getItem( 'token' )
    this.isAuthenticated = token != null
  }

  closeMenu(){
    this._menuCtrl.close()
  }

  jumpToUserForm(){
    //this._router.navigateByUrl( '/home/user-form' )
    this._popoverCtrl.dismiss()
  }

  logout() {
    this._popoverCtrl.dismiss()
  }
}