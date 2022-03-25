import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import Call from '@core/interfaces/call';
import Duty from '@core/interfaces/duty';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';

@Component({
  selector: 'call-form',
  templateUrl: './call-form.component.html',
  styleUrls: ['./call-form.component.scss'],
})
export class CallFormComponent implements OnInit {

  @Input() call: Call
  @Input() duties: Array<Duty>
  @Input() deptList: Array<string>

  tempDuty: any
  tempDestination: any
  editDuty: boolean = false
  editDestination: boolean = false

  constructor( 
    private _toast: ToastService,
    private _route: ActivatedRoute,
    private _callService: CallService ) { }

  ngOnInit() {
    this.tempDuty = this.call.duty
    this.tempDestination = this.call.destination
  }

  changeDestination( department ) {
    // Todo: change to form control
    this.editDestination = true
    this.tempDestination = department
  }

  forward(){
    console.log( this.tempDuty );
  }

}