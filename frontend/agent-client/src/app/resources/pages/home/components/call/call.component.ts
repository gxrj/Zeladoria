import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivationStart, Router } from '@angular/router';

import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import Call from '@core/interfaces/call'
import { HttpErrorResponse } from '@angular/common/http';
import { AttendanceService } from '@services/attendance/attendance.service';

@Component({
  selector: 'call',
  templateUrl: './call.component.html',
  styleUrls: ['./call.component.scss'],
})
export class CallComponent implements OnInit {

  titles = [ 'Serviço', 'Status', 'Bairro', 'Dt Postagem', 'Hora', 'Autor' ]
  calls: any = null
  duties: Array<any> = null
  deptList: Array<any> = null
  selectedCall: Call = null
  attendances: any = null

  constructor( 
    private _toast: ToastService, 
    private _route: ActivatedRoute,
    private _router: Router,
    private _callService: CallService,
    private _attendaceService: AttendanceService ) { }

  ngOnInit() {
    this.calls = this._route.snapshot.data.calls.result
    this.getAndStoreDuties()
    this.getAndStoreDepartments()
    this.listenRouterChanges()
  }

  private listenRouterChanges() {
    this._router.events
            .subscribe( 
              evt => {
                if ( evt instanceof ActivationStart ) {
                  this.reload()
                  this.return()
                }
              }
            )
  }

  selectCall( call: Call ) {
    this._attendaceService
          .listByCall( call )
            .subscribe( 
              res => {
                this.attendances = res.result
                this.selectedCall = call
              },
              error => {
                if( error instanceof HttpErrorResponse && error.status !== 401 && error.status )
                  this._toast
                          .displayMessage( 
                              `Falha no carregamento: ${ JSON.stringify( error.error ) }` )
              } )
  }

  getAndStoreDuties() {
    if( !this.duties ) {
      this.duties = JSON.parse( sessionStorage.getItem( 'duties' ) )
    }
  }

  getAndStoreDepartments() {
    if( this.duties && !this.deptList ) {
      this.deptList = this.duties.map( item => item.department.name )
      this.deptList = this.deptList.filter( ( item, index ) => this.deptList.indexOf( item ) === index )
      sessionStorage.setItem( 'deptList', JSON.stringify( this.deptList ) )
    }
  }

  return() {
    const fn = () => this.selectedCall = null
    setTimeout( fn, 1000 )
  }

  reload() {
    const user = sessionStorage.getItem( 'user' )
    
    if( !user ) {
      this._toast.displayMessage( 'Falha no carregamento, por favor reinicie sua sessão' )
      return null
    }

    const status = this._callService.getSelectedCallStatus()

    this._callService.list( JSON.parse( user ), status )
                      .subscribe(
                        resp =>  this.calls = resp.result,
                        error => {
                          if( error instanceof HttpErrorResponse && error.status !== 401 && error.status )
                            this._toast
                                    .displayMessage( 
                                        `Falha no carregamento: ${ JSON.stringify( error.error ) }` )
                        }
                      )
  }

  getTitle() {
    const status = this._callService.getSelectedCallStatus()
    switch( status ) {
      case 'Avaliada':
        return 'Ocorrências Avaliadas'
      case 'Respondida':
        return 'Ocorrências não Avaliadas'
      case 'Indeferida':
        return 'Ocorrências Indeferidas'
      default:
        return 'Ocorrências Abertas'
    }
  }
}