import { Component, OnInit } from '@angular/core';
import User from '@core/interfaces/user';

@Component({
  selector: 'user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss'],
})
export class UserFormComponent implements OnInit {

  account: User
  constructor() { }

  ngOnInit() {
    this.account = JSON.parse( sessionStorage.getItem( 'user' ) )
  }

}