import { Component, OnInit } from '@angular/core';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';
import User from '@core/interfaces/user';
import { IonDatetime, PopoverController } from '@ionic/angular';
import { AttendanceService } from '@services/attendance/attendance.service';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import { throwError } from 'rxjs';
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
    { label: 'Relação de ocorrências pelo tipo de usuário' },
    { label: 'Relação de ocorrências por setor' },
    { label: 'Relação de atendimentos avaliados' },
    { label: 'Relação de atendimentos avaliados por setor'  }
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

  getValues() {

    if( !this.start || !this.end ) {
      this._toast.displayMessage( 'Selecione o intervalo' )
      return
    }
  
    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )
    const beginning = new Date( this.start ).getTime()
    const final = new Date( this.end ).getTime()
    console.log( beginning );
    
    this._callService.listByInterval( beginning, final, user )
                        .pipe(
                          catchError( 
                            error => {
                              console.log( error )
                              return throwError( error )
                            } 
                          ) 
                        )
                        .subscribe( 
                          res => {
                            this.calls = res.result
                            console.log( this.calls )
                          }
                         )
  }

  getTimestampFormat( date: Date ) {
    let timestamp = `${date.getFullYear()}-${date.getMonth()}-${date.getDate()}`
    timestamp += `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`
    timestamp += '.098+00:00'

    return timestamp
  }
}