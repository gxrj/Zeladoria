import { Component, OnInit } from '@angular/core';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';
import User from '@core/interfaces/user';
import { IonDatetime, PopoverController } from '@ionic/angular';
import { AttendanceService } from '@services/attendance/attendance.service';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import { forkJoin, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss'],
})
export class ReportComponent implements OnInit {

  start: string = null
  end: string = null
  limit: string
  timeFormat = 'dd/MM/y - HH:mm'

  calls: Call[]
  attendances: Attendance[]

  options = [
    { label: 'Relação de ocorrências pelo tipo de usuário', hide: false },
    { label: 'Relação de ocorrências por setor', hide: this.hide() },
    { label: 'Relação de atendimentos avaliados', hide: false },
    { label: 'Relação de atendimentos avaliados por setor', hide: this.hide() }
  ]

  constructor(
    private _toast: ToastService,
    private _popoverCtrl: PopoverController,
    private _callService: CallService,
    private _attendanceService: AttendanceService ) { }

  ngOnInit() {
    this.limit = new Date().toISOString()
  }

  selectStartInterval( startDate: IonDatetime ) {
    this.start = startDate.value
    this._popoverCtrl.dismiss()
  }

  selectEndInterval( finalDate: IonDatetime ) {
    this.end = finalDate.value
    this._popoverCtrl.dismiss()
  }

  setLimit(): string {
    return this.limit
  }

  setMinimal(): string {
    return '2020-05-01'
  }

  hide() {
    const dept = JSON.parse( sessionStorage.getItem( 'user' ) ).department
    return dept !== 'Inova Macae'
  }

  getValues() {

    if( !this.start || !this.end ) {
      this._toast.displayMessage( 'Selecione o intervalo' )
      return
    }
    
    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )
    const beginning = new Date( this.start ).getTime()
    const final = new Date( this.end ).getTime()
    
    this.loadData( user, beginning, final )
  }

  async loadData( user: User, beginning: number, final: number ) {
    forkJoin( {
      calls: this._callService.listByInterval( beginning, final, user ),
      attendances: this._attendanceService.listByInterval( beginning, final, user )
    } )
    .pipe( catchError(  error => {
          console.log( error )
          return throwError( error )
        } 
      ) 
    )
    .subscribe( 
      res => {
        this.calls = res.calls.result
        this.attendances = res.attendances.result
      }
    )
  }
}