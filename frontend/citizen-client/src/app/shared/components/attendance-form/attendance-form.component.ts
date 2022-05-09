import { Component, Input, OnInit } from '@angular/core';
import { Attendance } from '@app/core/interfaces/attendance';

@Component({
  selector: 'app-attendance-form',
  templateUrl: './attendance-form.component.html',
  styleUrls: ['./attendance-form.component.scss'],
})
export class AttendanceFormComponent implements OnInit {

  @Input() attendance: Attendance

  constructor() { }

  ngOnInit() {}

}