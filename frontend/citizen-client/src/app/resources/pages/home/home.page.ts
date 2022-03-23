import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Duty from '@app/core/interfaces/duty';

@Component({
  selector: 'home-page',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  categories = null
  token: any
  selectedCategory = null
  selectedDuty: Duty = null

  constructor( private _route: ActivatedRoute,
               private _router: Router ) { }

  ngOnInit(): void {

    this.persistToken()
    this.persistCategories()
  }

  persistToken() {

    this.token = sessionStorage.getItem( 'token' )

    if( this.token )
      this.token = JSON.parse( this.token )
  }

  persistCategories() {
    this.categories = this._route.snapshot.data.categories.result ?? sessionStorage.getItem( 'categories' )

    if( this.categories && !this.categories.instanceof( String ) )
      sessionStorage.setItem( 'categories', JSON.stringify( this.categories ) )
    else if( this.categories.instanceof( String ) )
      this.categories = JSON.parse( this.categories )
  }

  selectCategory( category:any ) {
    this.selectedCategory = category
  }

  backToCategories() {
    this.selectedCategory = null
  }

  selectDutyAndOpenForm( duty:any ) {
    this.selectedDuty = { ...duty }
    this.selectedDuty.department = duty?.department?.name
    this.selectedDuty['category'] = this.selectedCategory.category

    this.openForm( this.selectedDuty )
  }

  openForm( duty: Duty ) {
    this._router.navigateByUrl( 'call', { state: duty } )
  }
}
