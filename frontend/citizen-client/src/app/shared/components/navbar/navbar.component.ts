import { Component, OnInit } from '@angular/core';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  isAuthenticated: boolean
  paths = [
    { url: '', label: 'Criar ocorrência' },
    { url: '', label: 'Minhas ocorrências' },
  ]

  constructor( private _menuCtrl: MenuController ) { }

  ngOnInit() {
    const token = sessionStorage.getItem( 'token' )
    this.isAuthenticated = token != null
  }

  closeMenu(){
    this._menuCtrl.close()
  }

}