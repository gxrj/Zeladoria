import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Attendance } from '@core/interfaces/attendance';
import { ToastService } from '@services/toast/toast.service';

@Component({
  selector: 'attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss'],
})
export class AttendanceComponent implements OnInit {

  attendances: Attendance[] = null
  titles = [ 'Protocolo', 'Data', 'Hora', 'Tipo', 'Responsável', 'Ações' ]

  constructor(
    private _router: Router,
    private _toast: ToastService, 
    private _route: ActivatedRoute ) { }

  ngOnInit() {
    this.attendances = this._route.snapshot.data.attendances.result
  }
}