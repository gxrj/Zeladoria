import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import Call from '@core/interfaces/call';
import Duty from '@core/interfaces/duty';
import { ModalController } from '@ionic/angular';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import { AttendanceFormComponent } from '../attendance-form/attendance-form.component';

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
  isPrank: boolean = false

  constructor( 
    private _fb: FormBuilder,
    private _toast: ToastService,
    private _route: ActivatedRoute,
    private _modal: ModalController,
    private _callService: CallService ) { }

  ngOnInit() {
    this.tempDuty = this.call.duty
    this.tempDestination = this.call.destination.name
  }

  checkDestination() {

    if( this.tempDuty.department.name !== this.tempDestination ) {
      this.tempDestination = this.tempDuty.department.name
      this.editDestination = true
      this.call.stattus = "Encaminhada"
    }
    if( !this.editDuty ) {
      this.tempDestination = this.call.destination.name
      this.editDestination = false
      this.call.stattus = "Em andamento"
    }
  }
  answer() {
    this.openModal()
  }

  forward() {
    if( this.editDuty )
      this.call.duty = this.tempDuty

    if( this.editDestination )
      this.call.destination.name = this.tempDestination

    this.answer()
  }

  delete() {
    
  }

  async openModal() {
    const modal = await this._modal.create( {
      component: AttendanceFormComponent
    } )
    return await modal.present();
  }

  closeModal(){
    this._modal.dismiss( {
      'dismissed': true
    } )
  }
}