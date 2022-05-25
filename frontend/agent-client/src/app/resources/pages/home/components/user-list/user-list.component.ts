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
  selectedUser: User
  titles = [ 'Nome', 'Matrícula', 'Secretaria', 'Ações' ]

  constructor( private _route: ActivatedRoute ) { }

  ngOnInit() {
    const current = JSON.parse( sessionStorage.getItem( 'user' ) )
    this.users = this._route.snapshot.data.users.result
    this.users = this.users.filter( el => el.username !== current.username )
  }

}