import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router'

@Component({
  selector: 'start-page',
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.css']
})
export class StartPageComponent implements OnInit {

  constructor( private router: Router ) { }

  ngOnInit(): void {
  }

  login() {

  }

  anonymousForward() {
    this.router.navigateByUrl( '/home' )
  }

}