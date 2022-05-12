import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'attendance-view-modal',
  templateUrl: './attendance-view-modal.component.html',
  styleUrls: ['./attendance-view-modal.component.scss'],
})
export class AttendanceViewModalComponent implements OnInit {

  dateFormat = 'dd/MM/y'
  timeFormat = 'HH:mm'

  constructor( 
    private _modal: ModalController ) { }

  ngOnInit() {}

  close() {
    this._modal.dismiss()
  }
}
