import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalController } from '@ionic/angular';

import User from '@core/interfaces/user';
import { DepartmentService } from '@services/department/department.service';
import { ToastService } from '@services/toast/toast.service';
import Department from '@core/interfaces/department';
import { UserService } from '@services/user/user.service';

@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit {

  users: User[]
  selectedUser: any
  modalOpen: boolean = false
  titles = [ 'Nome', 'Matrícula', 'Secretaria', 'Ações' ]
  newUser: User
  account: User
  deptList: any = null
  newDept: String
  oldDept: Department
  editDepartment: boolean = false

  constructor( 
    private _toast: ToastService,
    private _route: ActivatedRoute,
    private _modal: ModalController,
    private _userService: UserService,
    private _deptService: DepartmentService ) { }

  ngOnInit() {
    this.account = JSON.parse( sessionStorage.getItem( 'user' ) )
    this.setUserList()
    if( this.account.department === 'Inova Macae' )
      this.getDepartments()
  }

  setUserList() {
    this.users = this._route.snapshot.data.users.result
    this.users = this.users.filter( el => el.username !== this.account.username )
  }

  selectUser( user: User ) {
    this.selectedUser = user
    this.modalOpen = true
    this.oldDept = user.department 
    this.newDept= this.deptList[0].name
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

  specialDepartment(): boolean{
    return this.account.department === 'Inova Macae' ? true : false
  }

  save( user: User ) {
    if( this.editDepartment )
      user['department'].name = this.newDept
    this._userService.createOrUpdateUser( user )
          .subscribe( 
            res => { 
              this._toast.displayMessage( res.message )
              this.reload()
            },
            err => this._toast.displayMessage( err.message ) )
  }

  reload() {
    this._userService.list().subscribe( res => this.users = res.result )
    this.users = this.users.filter( el => el.username !== this.account.username )
  }

  create() {
    this.newUser = {
      username: '',
      name: '',
      cpf: '',
      password: '123',
      department: { name : this.account.department === 'Inova Macae' ? '' : this.account.department },
      is_admin: 'false'
    }    
  }
}