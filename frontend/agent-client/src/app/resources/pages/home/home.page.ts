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
    this.saveCurrentUserData()
    this.loadDutyList()
  }

  saveCurrentUserData() {

    this.account = sessionStorage.getItem( 'user' )

    if( !this.account ) {
      this.account = this._route.snapshot.data.account.result
      sessionStorage.setItem( 'user', JSON.stringify( this.account ) )
    }
    else
      this.account = JSON.parse( this.account )
  }

  loadDutyList() {
    if( !sessionStorage.getItem( 'duties' ) ) {
      let duties = this._route.snapshot.data.duties.result
      sessionStorage.setItem( 'duties', JSON.stringify( duties ) )
    }
  }
}