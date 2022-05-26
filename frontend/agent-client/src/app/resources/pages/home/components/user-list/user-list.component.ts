import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import User from '@core/interfaces/user';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit {

  users: User[]
  selectedUser: User
  modalOpen: boolean = false
  titles = [ 'Nome', 'Matrícula', 'Secretaria', 'Ações' ]

  constructor( 
    private _route: ActivatedRoute,
    private _modal: ModalController ) { }

  ngOnInit() {
    const current = JSON.parse( sessionStorage.getItem( 'user' ) )
    this.users = this._route.snapshot.data.users.result
    this.users = this.users.filter( el => el.username !== current.username )
  }

  selectUser( user: User ) {
    this.selectedUser = user
    this.modalOpen = true
  }

  closeModal() {
    this._modal.dismiss()
    this.modalOpen = false
  }

}