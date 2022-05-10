import { Component, Input, OnInit } from '@angular/core';
import { Attendance } from '@app/core/interfaces/attendance';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-attendance-form',
  templateUrl: './attendance-form.component.html',
  styleUrls: ['./attendance-form.component.scss'],
})
export class AttendanceFormComponent implements OnInit {

  @Input() attendance: Attendance
  dateFormat = 'dd/MM/y'
  timeFormat = 'HH:mm'

  constructor(
    private _modal: ModalController ) { }

  ngOnInit() {}

  close() {
    this._modal.dismiss()
  }

}