import { Component, OnInit } from '@angular/core';
import User from '@core/interfaces/user';
import { MenuController } from '@ionic/angular';
import { AttendanceService } from '@services/attendance/attendance.service';
import { CallService } from '@services/call/call.service';

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
        { label: 'Ocorrências Abertas', url: '/home/calls', status: 'Aberta' },
        { label: 'Ocorrências Avaliadas', url: '/home/calls', status: 'Avaliada' },
        { label: 'Ocorrências não Avaliadas', url: '/home/calls', status: 'Respondida' },
        { label: 'Ocorrências Indeferidas', url: '/home/calls', status: 'Indeferida' }
      ],
      visibility: true
    },
    { 
      title: 'Atendimentos', 
      paths: [ 
        { label: 'Atendimentos do Setor', url: '/home/attendances', filter: 'department' },
        { label: 'Meus Atendimentos', url: '/home/attendances', filter: 'agent' } 
      ],
      visibility: true 
    },
    {
      title: 'Administração',
      paths:[
        { label: 'Relatórios', url: '/home/charts' },
        { label: 'Cadastrar Colaborador', url: '/home/agent-cretion-form' }
      ],
      visibility: this.getVisibility()
    }
  ]
  
  constructor( 
    private _menuCtrl: MenuController,
    private _callService: CallService,
    private _attendanceService: AttendanceService ) { }

  ngOnInit() {}

  setPathParam( path: any ) {
    if( path.status && !path.filter ) {
      this.setCallStatus( path.status )
    }
    else if( path.filter && !path.status ){
      this.setAttendanceFilter( path.filter )
    }
  }

  getPathParam( path: any ): any {
    if( path.status && !path.filter ) {
      return { status: path.status }
    }
    else if( path.filter && !path.status ){
      return { filter: path.filter }
    }
  }

  setCallStatus( status: string ) {
    this._callService.setSelectedCallStatus( status )
    this.closeMenu()
  }

  setAttendanceFilter( filter: string ) {
    this._attendanceService.setListFilter( filter )
    this.closeMenu()
  }

  closeMenu(){
    this._menuCtrl.close()
  }

  getVisibility(): boolean {
    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )
    if( !user ) return false
    return JSON.parse( user.is_admin )
  }
}