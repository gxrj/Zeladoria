import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Attendance } from '@app/core/interfaces/attendance';
import Call from '@app/core/interfaces/call';
import { AttendanceService } from '@app/shared/services/attendance/attendance.service';
import { ToastService } from '@app/shared/services/toast/toast.service';

@Component({
  selector: 'call-history',
  templateUrl: './call-history.page.html',
  styleUrls: ['./call-history.page.scss'],
})
export class CallHistoryPage implements OnInit {

  titles = [ 'Serviço', 'Status', 'Bairro', 'Dia', 'Hora', 'Ações' ]
  history: Call[]
  selectedCall: Call = null
  attendances: Attendance[]

  constructor(
    private _route: ActivatedRoute,
    private _toast: ToastService,
    private _attendanceService: AttendanceService ) { }

  ngOnInit() {
    this.history = this._route.snapshot.data.history.result
  }

  getDetails( call: Call ) {
    this._attendanceService.list( call )
          .subscribe(
            res => {
              this.attendances = res.result 
              this.selectedCall = call
              console.log( this.attendances )
            },
            error => {
              if( error instanceof HttpErrorResponse && error.status !== 401 && error.status )
                this._toast.displayMessage( 
                      `Falha no carregamento: ${ JSON.stringify( error.error ) }` )
            }
          )
  }
}
