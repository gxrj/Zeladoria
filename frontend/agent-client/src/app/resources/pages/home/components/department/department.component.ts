import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import Department from '@core/interfaces/department';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.scss'],
})
export class DepartmentComponent implements OnInit {

  departments: Department[]
  selectedDepartment: Department
  titles = [ 'Nome', 'Ações' ]

  constructor( 
    private _route: ActivatedRoute,
    private _modal: ModalController ) { }

  ngOnInit() {
    this.departments = this._route.snapshot.data.departments.result
  }

  selectDepartment( Department: Department ) {
    this.selectedDepartment = Department
  }

  closeModal() {
    this._modal.dismiss()
  }
}