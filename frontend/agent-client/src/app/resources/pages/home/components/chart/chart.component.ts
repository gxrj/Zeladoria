import { Component, OnInit } from '@angular/core';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss'],
})
export class ChartComponent implements OnInit {

  start: any
  end: any
  limit: any
  timeFormat = 'dd/MM/y - HH:mm'

  calls: Call[]
  attendances: Attendance[]

  constructor() { }

  ngOnInit() {}

  getValues() {
    console.log( this.start )
  }
}