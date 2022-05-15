import { Component, OnInit } from '@angular/core';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';
import { IonDatetime, PopoverController } from '@ionic/angular';

@Component({
  selector: 'report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss'],
})
export class ReportComponent implements OnInit {

  start: any
  end: any
  limit: any
  timeFormat = 'dd/MM/y - HH:mm'

  calls: Call[]
  attendances: Attendance[]

  constructor(
    private _popoverCtrl: PopoverController) { }

  ngOnInit() {}

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
}