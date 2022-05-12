import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';

import { AttendanceService } from '@services/attendance/attendance.service';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';

@Component({
  selector: 'attendance-creation-modal',
  templateUrl: './attendance-creation-modal.component.html',
  styleUrls: ['./attendance-creation-modal.component.scss'],
})
export class AttendanceCreationModalComponent implements OnInit {

  @Input() call: Call
  @Input() toForward: boolean
  isRejected: boolean = false
  attendance: Attendance

  constructor( 
    private _toast: ToastService,
    private _router: Router,
    private _modal: ModalController,
    private _callService: CallService,
    private _attendanceService: AttendanceService ) {

      this.attendance = {
        protocol: '',
        description: '',
        call: null,
        responsible: null,
        type: this.toForward ? 'encaminhamento':'resposta'
      }
  }

  ngOnInit() {
    this.attendance.responsible = JSON.parse( 
                                        sessionStorage.getItem( 'user' ) )
  }

  send() {

    this.prepareAttendance()
    
    const response = forkJoin( [
      this._attendanceService.create( this.attendance ),
      this._callService.update( this.call )
    ] )
    
    response.subscribe(
      () => { 
        this._toast.displayMessage( "Gravado com sucesso" )
        this.close()
        this._router.navigateByUrl( '/home' ) 
      },
      error => {
        this._toast.displayMessage( 
          `Falha na gravação: ${ JSON.stringify( error ) }` )
        console.log( error )
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
    this.call.status = this.isRejected ? "Indeferida" : this.call.status
    this.attendance.protocol =  date + usernameHex
    this.attendance.issued_at = date
    this.attendance.call = this.call
  }
}