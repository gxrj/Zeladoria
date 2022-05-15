import { Component, OnInit } from '@angular/core';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';
import { IonDatetime, PopoverController } from '@ionic/angular';
import { AttendanceService } from '@services/attendance/attendance.service';
import { CallService } from '@services/call/call.service';

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

  getValues() {

    console.log( this.start )
  }

  setLimit(): string {
    return this.limit
  }

  setMinimal(): string {
    return '2020-05-01'
  }
}