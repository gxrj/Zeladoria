import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Attendance } from '@app/core/interfaces/attendance';
import Call from '@app/core/interfaces/call';
import { AttendanceService } from '@app/shared/services/attendance/attendance.service';
import { CallService } from '@app/shared/services/call/call.service';
import { ToastService } from '@app/shared/services/toast/toast.service';
import { ModalController } from '@ionic/angular';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-attendance-form',
  templateUrl: './attendance-form.component.html',
  styleUrls: ['./attendance-form.component.scss'],
})
export class AttendanceFormComponent implements OnInit {

  @Input() attendance: Attendance
  @Input() call: Call
  dateFormat = 'dd/MM/y'
  timeFormat = 'HH:mm'
  ratingRequired: boolean
  hideSaveButton: boolean = false

  constructor(
    private _router: Router,
    private _toast: ToastService,
    private _modal: ModalController,
    private _callService: CallService,
    private _attendanceService: AttendanceService ) { }

  ngOnInit() { 
    this.attendance.feedback = !this.attendance.feedback? '' : this.attendance.feedback
    this.hideSaveButton = this.call.status === 'Respondida' ? false : true 
  }

  close() {
    this._modal.dismiss()
  }

  upVote() {
    this.ratingRequired = false
    this.call.status = 'Finalizada'
  }

  downVote() {
    this.ratingRequired = true
    this.call.status = 'Nao resolvida'
  }

  save() {
    if( this.ratingRequired && this.attendance.feedback === '' ) {
      this._toast.displayMessage( 'É necessário descrever o motivo da avaliação' )
      return
    }

    const response = forkJoin( [
      this._attendanceService.update( this.attendance ),
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
}