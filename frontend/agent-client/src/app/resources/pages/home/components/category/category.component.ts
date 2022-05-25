import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import Category from '@core/interfaces/category';

@Component({
  selector: 'category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
})
export class CategoryComponent implements OnInit {

  categories: Category[]

  constructor( private _route: ActivatedRoute ) { }

  ngOnInit() {
    this.categories = this._route.snapshot.data.categories
  }

}