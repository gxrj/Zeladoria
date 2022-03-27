import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

import { AttendanceService } from '@services/attendance/attendance.service';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import Call from '@core/interfaces/call';

@Component({
  selector: 'app-attendance-form',
  templateUrl: './attendance-form.component.html',
  styleUrls: ['./attendance-form.component.scss'],
})
export class AttendanceFormComponent implements OnInit {

  @Input() call: Call
  @Input() toForward: boolean
  isRejected: boolean = false

  constructor( 
    private _toast: ToastService,
    private _modal: ModalController,
    private _callService: CallService,
    private _attendanceService: AttendanceService ) { }

  ngOnInit() {}

  send() {
    
    this.call.status = this.toForward ? "Encaminhada" : "Respondida"
    this.close()
  }

  close() {
    this._modal.dismiss()
  }
}