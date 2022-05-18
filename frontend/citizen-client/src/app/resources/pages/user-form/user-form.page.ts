import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import User from '@app/core/interfaces/user';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'user-form',
  templateUrl: './user-form.page.html',
  styleUrls: ['./user-form.page.scss'],
})
export class UserFormPage implements OnInit {

  account: User

  constructor(
    private _route: ActivatedRoute,
    private _modalCtrl: ModalController ) { }

  ngOnInit() {
    this.account = this._route.snapshot.data.account
  }

  changePassword() {
    console.log( 'Password change TO-DO' )
  }

  closeModal() {
    this._modalCtrl.dismiss()
  }
}