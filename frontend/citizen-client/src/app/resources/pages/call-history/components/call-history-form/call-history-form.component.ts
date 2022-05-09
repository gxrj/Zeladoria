import { Component, Input, OnInit } from '@angular/core';
import { Attendance } from '@app/core/interfaces/attendance';

import Call from '@app/core/interfaces/call';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'call-history-form',
  templateUrl: './call-history-form.component.html',
  styleUrls: ['./call-history-form.component.scss'],
})
export class CallHistoryFormComponent implements OnInit {

  @Input() call: Call
  @Input() attendances: Attendance[]

  constructor( private _modal: ModalController ) { }

  ngOnInit() {}
}
