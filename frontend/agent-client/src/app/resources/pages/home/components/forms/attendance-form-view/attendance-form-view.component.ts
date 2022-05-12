import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-attendance-form-view',
  templateUrl: './attendance-form-view.component.html',
  styleUrls: ['./attendance-form-view.component.scss'],
})
export class AttendanceFormViewComponent implements OnInit {

  dateFormat = 'dd/MM/y'
  timeFormat = 'HH:mm'

  constructor( 
    private _modal: ModalController ) { }

  ngOnInit() {}

  close() {
    this._modal.dismiss()
  }
}
