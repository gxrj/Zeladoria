import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'attendance-view-form',
  templateUrl: './attendance-view-form.component.html',
  styleUrls: ['./attendance-view-form.component.scss'],
})
export class AttendanceViewFormComponent implements OnInit {

  dateFormat = 'dd/MM/y'
  timeFormat = 'HH:mm'

  constructor( 
    private _modal: ModalController ) { }

  ngOnInit() {}

  close() {
    this._modal.dismiss()
  }
}
