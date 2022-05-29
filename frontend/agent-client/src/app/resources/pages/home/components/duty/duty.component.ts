import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

import Duty from '@core/interfaces/duty';
import User from '@core/interfaces/user';
import { DepartmentService } from '@services/department/department.service';
import { ToastService } from '@services/toast/toast.service';
import Department from '@core/interfaces/department';
import { CategoryService } from '@services/category/category.service';

@Component({
  selector: 'duty',
  templateUrl: './duty.component.html',
  styleUrls: ['./duty.component.scss'],
})
export class DutyComponent implements OnInit {

  duties: Duty[]
  selectedDuty: Duty
  modalOpen: boolean = false
  titles = [ 'Descrição', 'Categoria', 'Secretaria', 'Ações' ]

  account: User
  deptList: any = null
  newDept: String
  oldDept: String
  editDepartment: boolean = false
  categoryList: any = null
  newCategory: String
  oldCategory: String
  editCategory: boolean = false

  constructor(
    private _toast: ToastService,
    private _modal: ModalController,
    private _deptService: DepartmentService,
    private _categoryService: CategoryService, ) { }

  ngOnInit() {
    this.account = JSON.parse( sessionStorage.getItem( 'user' ) )
    this.loadDepartmentDuties()
    if( this.checkPrivilege() ) {
      this.getDepartments()
      this.getCategories()
    }
  }

  private loadDepartmentDuties() {
    let array = JSON.parse( sessionStorage.getItem( 'duties' ) )
    let toFilter = this.account.department === 'Inova Macae'
    this.duties = toFilter ? array : array.filter( el => el.department.name === this.account.department )
  }

  selectDuty( duty: Duty ) {
    this.selectedDuty = duty
    this.modalOpen = true
    this.oldDept = duty.department['name'] 
    this.oldCategory = duty.category.name
    if( this.checkPrivilege() ) {
      this.newDept = this.deptList[0].name
      this.newCategory = this.categoryList[0].name
    }
  }

  closeModal() {
    this._modal.dismiss()
    this.modalOpen = false
  }

  getDepartments() {
    this.deptList = sessionStorage.getItem( 'deptList' )
    if( this.deptList == null ) {
      this._deptService.list()
                .subscribe(
                  res => this.deptList = res.result,
                  err => this._toast.displayMessage( err ) )
    }
    else
      this.deptList = JSON.parse( this.deptList )
  }

  hide( dept: Department ): boolean {
    return dept.name === 'Inova Macae' ? true : false
  }

  save( duty: Duty ) {
    if( this.editDepartment )
      duty['department']['name'] = this.newDept

    if( this.editCategory )
      duty['category']['name'] = this.newCategory

    console.log( duty );
    
  }

  getCategories() {
    this._categoryService.getCategories().subscribe( res => this.categoryList = res.result )
  }

  checkPrivilege(): boolean {
    return this.account.department === 'Inova Macae'
  }
}