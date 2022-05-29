import { Component, OnInit } from '@angular/core';
import { IonDatetime, PopoverController } from '@ionic/angular';
import { ChartData } from 'chart.js';
import { forkJoin, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AttendanceService } from '@services/attendance/attendance.service';
import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import { Attendance } from '@core/interfaces/attendance';

import Call from '@core/interfaces/call';
import User from '@core/interfaces/user';
import Duty from '@core/interfaces/duty';
import { ChartComponent } from '../chart/chart.component';


@Component({
  selector: 'report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss'],
})
export class ReportComponent implements OnInit {

  timeFormat = 'dd/MM/y - HH:mm'

  start: string = null
  end: string = null
  limit: string

  calls: Call[] = null
  attendances: Attendance[] = null

  chartData: ChartData = {
    labels: [],
    datasets: []
  }

  selectedItem: any
  render: boolean = true
  departmentName: string

  options = [
    { label: 'Selecione o tipo de relatório', display: 'all', value: null },
    { 
      label: 'Relação: ocorrências x classe de usuário', 
      display: 'special', 
      value: { 
        type: 'pie', 
        condition: () => this.calls != null,
        getLabels: () => [ 'Anônimo', 'Autenticado' ],
        getData: () => [
          this.calls.filter( el => el.author.name === 'anonimo' ).length,
          this.calls.filter( el => el.author.name !== 'anonimo' ).length
        ],
        getTotal: () => this.calls.length
      } 
    },
    { 
      label: 'Relação: ocorrências x serviço', 
      display: 'specific',
      value: {
        type: 'bar', 
        condition: () => this.calls != null,
        getLabels: () => this.getDuties().map( el => el.split( ' ' ) ),
        getData: () => this.countCallsPerDuty(),
        getTotal: () => this.filterCallByDepartmentOrNot( this.calls, this.getDepartment() ).length
      }
    },
    { 
      label: 'Relação: ocorrências x categoria', 
      display: 'special',
      value: {
        type: 'bar', 
        condition: () => this.calls != null,
        getLabels: () => this.getCategories().map( el => el.split( ' ' ) ),
        getData: () => this.countCallsPerCategory(),
        getTotal: () => this.calls.length
      }
    },
    { 
      label: 'Relação: avaliação x serviço', 
      display: 'specific',
      value: {
        type: 'bar', 
        condition: () => this.attendances != null,
        getTotal: () => 0
      } 
    },
    { 
      label: 'Relação: avaliação x setor', 
      display: 'special',
      value: {
        type: 'bar', 
        condition: () => this.attendances != null,
        getTotal: () => 0
      } 
    },
    { 
      label: 'Relação: ocorrências & atendimentos x bairro', 
      display: 'all',
      value: {
        type: 'bar', 
        condition: () => this.calls != null,
        getTotal: () => 0
      } 
    },
    { 
      label: 'Relação: ocorrências & atendimentos x setor', 
      display: 'special',
      value: {
        type: 'bar', 
        condition: () => this.attendances != null,
        getTotal: () => 0
      } 
    }
  ]

  constructor(
    private _toast: ToastService,
    private _popoverCtrl: PopoverController,
    private _callService: CallService,
    private _attendanceService: AttendanceService ) { }

  ngOnInit() {
    this.limit = new Date().toISOString()
    this.selectedItem = this.options[0]
    this.departmentName = JSON.parse( sessionStorage.getItem( 'user' ) ).department
  }

  selectStartInterval( startDate: IonDatetime ) {
    this.start = startDate.value
    this._popoverCtrl.dismiss()
  }

  selectEndInterval( finalDate: IonDatetime ) {
    this.end = finalDate.value
    this._popoverCtrl.dismiss()
  }

  setLimit(): string {
    return this.limit
  }

  setMinimal(): string {
    return '2020-05-01'
  }

  hide( display: 'all' | 'special' | 'specific' ): boolean {
    if( display === 'all' ) return false
    if( display === 'specific' && this.departmentName === 'Inova Macae' ) return true
    if( display === 'special' && this.departmentName !== 'Inova Macae' ) return true

    return false
  }

  getValues() {

    if( !this.start || !this.end ) {
      this._toast.displayMessage( 'Selecione o intervalo' )
      return
    }
    
    const user: User = JSON.parse( sessionStorage.getItem( 'user' ) )
    const beginning = new Date( this.start ).getTime()
    const final = new Date( this.end ).getTime()
    
    this.loadData( user, beginning, final )
  }

  clearValues() {
    this.start = this.end = null
    this.calls = this.attendances = null
  }

  private async loadData( user: User, beginning: number, final: number ) {
    forkJoin( {
      calls: this._callService.listByInterval( beginning, final, user ),
      attendances: this._attendanceService.listByInterval( beginning, final, user )
    } )
    .pipe( catchError(  error => {
          console.log( error )
          return throwError( error )
        } 
      ) 
    )
    .subscribe( 
      res => {
        this.calls = res.calls.result
        this.attendances = res.attendances.result
      }
    )
  }

  select( item: any ) {
    this.render = false
    this.chartData.datasets = null
    if( item.value.condition() ) {
      this.selectedItem = item
      this.chartData.labels = item.value.getLabels()
      this.chartData.datasets = [ { data: item.value.getData() } ]
    }
    setTimeout( () => this.render = true, 150 )
  }

  isIntervalEmpty(): boolean {
    return !this.start || !this.end
  }

  areValuesEmpty(): boolean {
    return !this.calls && !this.attendances
  }

  getChartTitleLabel() {
    const startDate = new Date( this.start ).toLocaleDateString('pt-Br')
    const endDate = new Date( this.end ).toLocaleDateString('pt-Br')
    return `${ this.selectedItem.label } entre ${ startDate } e ${ endDate }`
  }

  getDuties(): string[] {
    let result: string[] = []
    const deptName = this.getDepartment()
    if( deptName )
      this.filterCallByDepartmentOrNot( this.calls, deptName )
                  .map( el => el.duty.description )
                    .forEach( el => result.includes( el ) ? '' : result.push( el ) )

    return result
  }

  private filterCallByDepartmentOrNot( calls: Call[], deptName: String ): Call[] {
    if( this.userFromSpecialDepartment() ) return this.calls
    return calls.filter( el => el.duty.department.name === deptName )
  }

  private userFromSpecialDepartment(): boolean {
    return JSON.parse( sessionStorage.getItem( 'user' ) ).department === 'Inova Macae'
  }

  private getDepartment(): string {
    return JSON.parse( sessionStorage.getItem( 'user' ) ).department
  }

  getCategories(): string[] {
    let result: string[] = []
    const deptName = this.getDepartment()
    if( deptName )
      this.filterCallByDepartmentOrNot( this.calls, deptName )
                .map( el => el.duty.category.name )
                  .forEach( el => result.includes( el ) ? '' : result.push( el ) )
    return result
  }

  getDistricts(): string[] {
    let result: string[] = []
    const deptName = this.getDepartment()
    if( deptName )
      this.filterCallByDepartmentOrNot( this.calls, deptName )
                .map( el => el.duty.address.district )
                  .forEach( el => result.includes( el ) ? '' : result.push( el ) )
    return result
  }

  countCallsPerDuty(): number[] {
    let result: Array<number> = []

    for( let duty of this.getDuties() )
      result.push( this.calls.filter( el => el.duty.description === duty ).length )

    return result
  }

  countCallsPerCategory(): number[] {
    let result: Array<number> = []

    for( let category of this.getCategories() )
      result.push( this.calls.filter( el => el.duty.category.name === category ).length )

    return result
  }

  countCallsPerDistricts(): number[] {
    let result: Array<number> = []

    for( let district of this.getDistricts() )
      result.push( this.calls.filter( el => el.duty.address.district === district ).length )

    return result
  }
}