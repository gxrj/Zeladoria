import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivationStart, Router } from '@angular/router';
import { Attendance } from '@core/interfaces/attendance';
import User from '@core/interfaces/user';
import { AttendanceService } from '@services/attendance/attendance.service';
import { ToastService } from '@services/toast/toast.service';

@Component({
  selector: 'attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss'],
})
export class AttendanceComponent implements OnInit {

  listHeaders = [ 'Protocolo', 'Data', 'Hora', 'Tipo', 'Responsável', 'Ações' ]
  attendances: Attendance[] = null
  user: User = null
  filter: string = ''
  selectedAttendance: Attendance = null

  constructor(
    private _router: Router,
    private _toast: ToastService, 
    private _route: ActivatedRoute,
    private _attendanceService: AttendanceService ) { }

  ngOnInit() {
    this.filter = this._attendanceService.getListFilter()
    this.user = JSON.parse( sessionStorage.getItem( 'user' ) )
    this.attendances = this._route.snapshot.data.attendances.result
    this.listenRouterChanges()
  }

  private listenRouterChanges() {
    this._router.events
            .subscribe( 
              evt => {
                if ( evt instanceof ActivationStart ) {
                  this.reload()
                  setTimeout( () => this.return(), 1000 )
                }
              }
            )
  }

  reload() {
    this._attendanceService
          .listAttendanceByFilter( this.filter, this.user )
            .subscribe( 
              resp => this.attendances = resp.result,
              error => {
                if( error instanceof HttpErrorResponse && error.status !== 401 && error.status )
                  this._toast
                          .displayMessage( 
                              `Falha no carregamento: ${ JSON.stringify( error.error ) }` ) 
              }
            )
  }

  return() {
    this.selectedAttendance = null
  }

  getTitle(): string {
    this.filter = this._attendanceService.getListFilter()
    return this.filter === 'agent' ? 'Meus Atendimentos' : 'Atendimentos do Setor'
  }

  selectElement( attendance: Attendance ) {
    this.selectedAttendance = attendance
  }
}