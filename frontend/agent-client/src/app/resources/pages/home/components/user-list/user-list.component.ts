import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import User from '@core/interfaces/user';

@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit {

  users: User[]
  constructor( private _route: ActivatedRoute ) { }

  ngOnInit() {
    this.users = this._route.snapshot.data.users
  }

}