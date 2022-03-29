import { Component, OnInit } from '@angular/core';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  group = [
    { 
      title: 'Ocorrências', 
      paths: [ 
        { label: 'Ocorrências Abertas', url: '/home/calls' },
        { label: 'Ocorrências Encaminhadas', url: '' },
        { label: 'Ocorrências Indeferidas', url: '' },
        { label: 'Ocorrências Respondidas', url: '' }   
      ] 
    },
    { 
      title: 'Atendimentos', 
      paths: [ 
        { label: 'Atendimentos do Setor', url: '/home/attendances' },
        { label: 'Meus Atendimentos', url: '' } 
      ] 
    },
    { 
      title: 'Feedbacks', 
      paths: [ 
        { label: 'Feedbacks dos meus atendimentos', url: '/home/feedbacks' } 
      ] 
    }
  ]
  
  constructor( private _menuCtrl: MenuController ) { }

  ngOnInit() {}

  closeMenu(){
    this._menuCtrl.close()
  }
}