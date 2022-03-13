import { Component, OnInit } from '@angular/core';

import mockServicesList from './mock-services-list'

@Component({
  selector: 'home-page',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  categories = mockServicesList
  token: any
  selectedCategory = null
  
  constructor() { }

  ngOnInit(): void {
    
    this.token = sessionStorage.getItem( 'token' )

    if( this.token )
      this.token = JSON.parse( this.token )
  }

  selectCategory( category:any ) {
    this.selectedCategory = category
  }

  backToCategories() {
    this.selectedCategory = null
  }
}