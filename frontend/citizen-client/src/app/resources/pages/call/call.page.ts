import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Duty from '@app/core/interfaces/duty';

@Component({
  selector: 'call-page',
  templateUrl: './call.page.html',
  styleUrls: ['./call.page.scss'],
})
export class CallPage implements OnInit {

  duty: Duty = null
  districts = null

  constructor( 
    private _router: Router,
    private _route: ActivatedRoute ) { }

  ngOnInit() {
    this.duty = window.history.state
    this.districts = this._route.snapshot.data.districts.result
  }

  back() {
    this._router.navigateByUrl( '/home' )
  }
}