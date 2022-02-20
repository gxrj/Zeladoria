import { Component, OnInit } from '@angular/core';

import services from './services'

@Component({
  selector: 'home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  services = services
  
  constructor() { }

  ngOnInit(): void {
  }

}
