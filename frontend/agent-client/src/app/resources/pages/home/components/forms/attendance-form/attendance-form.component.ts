import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

import { AttendanceService } from '@services/attendance/attendance.service';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import Call from '@core/interfaces/call';
import { Attendance } from '@core/interfaces/attendance';
import { HttpResponse } from '@angular/common/http';
import { Message } from '@core/interfaces/message';

@Component({
  selector: 'app-attendance-form',
  templateUrl: './attendance-form.component.html',
  styleUrls: ['./attendance-form.component.scss'],
})
export class AttendanceFormComponent implements OnInit {

  @Input() call: Call
  @Input() toForward: boolean
  isRejected: boolean = false
  attendance: Attendance

  constructor( 
    private _toast: ToastService,
    private _modal: ModalController,
    private _callService: CallService,
    private _attendanceService: AttendanceService ) {

      this.attendance = {
        protocol: '',
        description: '',
        call: null,
        responsible: null
      }
  }

  ngOnInit() {
    this.attendance.responsible = JSON.parse( 
                                        sessionStorage.getItem( 'user' ) )
  }

  send() {

    this.prepareAttendance()

    this._attendanceService
          .create( '/agent/attendance/new', this.attendance )
            .subscribe(
                (res: HttpResponse<Message> ) => { 
                  this._toast.displayMessage( res.body.message )
                  this.close() 
                },
                error => {
                  console.log( error.error )
                  this._toast.displayMessage( 
                    `Falha na gravação: ${ JSON.stringify( error.error ) }` )

                  this.close()
                }
            )
  }

  close() {
    this._modal.dismiss()
  }

  private prepareAttendance() {
    const date = Date.now()
    const usernameHex = parseInt( this.attendance.responsible?.username, 16 ).toString()

    this.call.status = this.toForward ? "Encaminhada" : "Respondida"
    this.attendance.protocol =  date + usernameHex
    this.attendance.issued_at = date
    this.attendance.call = this.call
  }
}