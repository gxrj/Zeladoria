import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-attendance-form',
  templateUrl: './attendance-form.component.html',
  styleUrls: ['./attendance-form.component.scss'],
})
export class AttendanceFormComponent implements OnInit {

  isRejected: boolean = false

  constructor( private _modal: ModalController ) { }

  ngOnInit() {}

  close() {
    this._modal.dismiss()
  }
}