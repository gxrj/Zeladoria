import { Component, OnInit } from '@angular/core';
import User from '@core/interfaces/user';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss'],
})
export class UserFormComponent implements OnInit {

  account: User
  constructor(
    private _modal: ModalController ) { }

  ngOnInit() {
    this.account = JSON.parse( sessionStorage.getItem( 'user' ) )
  }

  closeModal() {
    this._modal.dismiss()
  }
}