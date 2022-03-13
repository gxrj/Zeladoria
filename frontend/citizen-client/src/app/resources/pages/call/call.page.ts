import { Component, OnInit } from '@angular/core';
import Duty from '@app/core/interfaces/duty';

@Component({
  selector: 'call-page',
  templateUrl: './call.page.html',
  styleUrls: ['./call.page.scss'],
})
export class CallPage implements OnInit {

  public duty: Duty = {
    id: null,
    description: 'Bueiro sem tampa',
    department: 'Infraestrutura',
    category: null
  }

  constructor() { }

  ngOnInit() {
  }

}
