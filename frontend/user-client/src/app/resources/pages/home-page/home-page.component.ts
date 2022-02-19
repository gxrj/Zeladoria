import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  array = [ 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17 ]

  constructor() { }

  ngOnInit(): void {
  }

}
