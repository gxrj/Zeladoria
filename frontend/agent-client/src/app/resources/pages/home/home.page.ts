import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  account: any = null

  constructor( private _route: ActivatedRoute ) { }

  ngOnInit() {
    //this.saveCurrentUserData()
  }

  saveCurrentUserData() {

    this.account = sessionStorage.getItem( 'user' )

    if( !this.account ) {
      this.account = this._route.snapshot.data.account
      sessionStorage.setItem( 'user', JSON.stringify( this.account ) )
    }
    else
      this.account = JSON.parse( this.account )
  }
}