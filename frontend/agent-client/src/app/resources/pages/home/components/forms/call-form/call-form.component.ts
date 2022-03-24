import { Component, Input, OnInit } from '@angular/core';

import Call from '@core/interfaces/call';

@Component({
  selector: 'call-form',
  templateUrl: './call-form.component.html',
  styleUrls: ['./call-form.component.scss'],
})
export class CallFormComponent implements OnInit {

  @Input() call: Call

  editDuty: boolean = false
  editDestination: boolean = false

  constructor() { }

  ngOnInit() {}

}