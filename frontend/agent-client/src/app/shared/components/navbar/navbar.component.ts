import { Component, OnInit } from '@angular/core';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  paths = [
    { url: '/home/calls', label: 'Ocorrências' },
    { url: '/home/forwarded-calls', label: 'Encaminhamentos' },
    { url: '/home/attendances', label: 'Atendimentos' },
    { url: '/home/feedbacks', label: 'Feedbacks' }
  ]
  
  constructor( private _menuCtrl: MenuController ) { }

  ngOnInit() {}

  closeMenu(){
    this._menuCtrl.close()
  }
}