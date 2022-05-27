import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import User from '@core/interfaces/user';
import { MenuController, PopoverController } from '@ionic/angular';
import { AttendanceService } from '@services/attendance/attendance.service';
import { AuthService } from '@services/auth/auth.service';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  @Input() account: User
  dateFormat = 'dd/MM/y'
  group = [
    { 
      title: 'Ocorrências', 
      paths: [ 
        { label: 'Ocorrências Abertas', url: '/home/calls', status: 'Aberta' },
        { label: 'Ocorrências Avaliadas', url: '/home/calls', status: 'Avaliada' },
        { label: 'Ocorrências não Avaliadas', url: '/home/calls', status: 'Respondida' },
        { label: 'Ocorrências Indeferidas', url: '/home/calls', status: 'Indeferida' }
      ],
      visibility: this.isParticularDepartment()
    },
    { 
      title: 'Atendimentos', 
      paths: [ 
        { label: 'Atendimentos do Setor', url: '/home/attendances', filter: 'department' },
        { label: 'Meus Atendimentos', url: '/home/attendances', filter: 'agent' } 
      ],
      visibility: this.isParticularDepartment()
    },
    {
      title: 'Administração',
      paths:[
        { label: 'Gerenciar Colaboradores', url: '/home/user-list' },
        { label: 'Gerenciar Serviços', url: '/home/duties' },
      ],
      visibility: this.getVisibility()
    },
    {
      title: 'Administração Geral',
      paths: [
        { label: 'Gerenciar Categorias', url: '/home/categories' },
        { label: 'Gerenciar Secretarias', url: '/home/departments' }
      ],
      visibility: !this.isParticularDepartment()
    },
    {
      title: 'Indicadores',
      paths:[
        { label: 'Relatórios', url: '/home/reports' }
      ],
      visibility: this.getVisibility()
    }
  ]

  constructor( 
    private _router: Router,
    private _toast: ToastService,
    private _menuCtrl: MenuController,
    private _authService: AuthService,
    private _callService: CallService,
    private _popoverCtrl: PopoverController,
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

  closeMenu() {
    this._menuCtrl.close()
  }

  getVisibility(): boolean {
    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )
    if( !user ) return false
    return JSON.parse( user.is_admin )
  }

  isParticularDepartment(): boolean {
    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )
    if( !user ) return false
    return user.department !== 'Inova Macae'
  }

  jumpToUserForm(){
    this._router.navigateByUrl( '/home/user-form' )
    this._popoverCtrl.dismiss()
  }

  logout() {
    this._popoverCtrl.dismiss()
    this._authService.revokeToken()
              .subscribe( 
                res => { 
                  console.log( res );
                  this._toast.displayMessage( 'Encerrando a sessão' )
                }, 
                error => this._toast.displayMessage( error ) )
    sessionStorage.clear()
    setTimeout( () => this._router.navigateByUrl( '/logout' ), 1000 )
  }

  getMyName(): string {
    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )
    return user.name
  }
}