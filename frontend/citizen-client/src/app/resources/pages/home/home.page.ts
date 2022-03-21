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