import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'home-page',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  categories = null
  token: any
  selectedCategory = null
  
  constructor( private _route: ActivatedRoute ) { }

  ngOnInit(): void {
    
    this.token = sessionStorage.getItem( 'token' )
    this.categories = JSON.parse( sessionStorage.getItem( 'categories' ) )

    if( this.token )
      this.token = JSON.parse( this.token )

    this.categories = this._route.snapshot.data.categories.result
  }

  selectCategory( category:any ) {
    this.selectedCategory = category
  }

  backToCategories() {
    this.selectedCategory = null
  }
}