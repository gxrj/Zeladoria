import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import Category from '@core/interfaces/category';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
})
export class CategoryComponent implements OnInit {

  categories: Category[]
  selectedCategory: Category
  titles = [ 'Nome', 'Ações' ]

  constructor( 
    private _route: ActivatedRoute,
    private _modal: ModalController ) { }

  ngOnInit() {
    this.categories = this._route.snapshot.data.categories.result
  }

  selectCategory( Category: Category ) {
    this.selectedCategory = Category
  }

  closeModal() {
    this._modal.dismiss()
  }
}