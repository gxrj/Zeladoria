import { Component, OnInit } from '@angular/core';

import Duty from '@core/interfaces/duty';
import User from '@core/interfaces/user';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'duty',
  templateUrl: './duty.component.html',
  styleUrls: ['./duty.component.scss'],
})
export class DutyComponent implements OnInit {

  duties: Duty[]
  selectedDuty: Duty
  titles = [ 'Descrição', 'Secretaria', 'Ações' ]

  constructor(
    private _modal: ModalController ) { }

  ngOnInit() {
    this.loadDepartmentDuties()
  }

  private loadDepartmentDuties() {
    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )
    let array = JSON.parse( sessionStorage.getItem( 'duties' ) )
    let toFilter = user.department === 'Inova Macae'
    this.duties = toFilter ? array : array.filter( el => el.department.name === user.department )
  }

  selectDuty( duty: Duty ) {
    this.selectedDuty = duty
  }

  closeModal() {
    this._modal.dismiss()
  }
}