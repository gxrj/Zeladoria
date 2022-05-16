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
    datasets: [ { data: [] } ]
  }

  selectedItem: any

  options = [
    { label: 'Selecione o tipo de relatório', secure: false, value: null },
    { 
      label: 'Total de corrências por classe de usuário', 
      secure: false, 
      value: { 
        type: 'pie', 
        condition: () => this.calls != null,
        getLabels: () => [ 'Anônimo', 'Autenticado' ],
        getData: () => [
          this.calls.filter( el => el.author.name === 'anonimo' ).length,
          this.calls.filter( el => el.author.name !== 'anonimo' ).length
        ]
      } 
    },
    { 
      label: 'Total de ocorrências por setor', 
      secure: true,
      value: {
        type: 'pie', 
        condition: () => this.calls != null,
      }
    },
    { 
      label: 'Relação de atendimentos avaliados', 
      secure: false,
      value: {
        type: 'bar', 
        condition: () => this.attendances != null
      }
    },
    { 
      label: 'Relação de atendimentos avaliados por setor', 
      secure: true,
      value: {
        type: 'bar', 
        condition: () => this.attendances != null
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

  hide( secure: boolean = false  ) {
    if( !secure ) return false
    const dept = JSON.parse( sessionStorage.getItem( 'user' ) ).department
    return dept !== 'Inova Macae'
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

  async loadData( user: User, beginning: number, final: number ) {
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
    if( item.value.condition() ) {
      this.chartData.labels = item.value.getLabels()
      this.chartData.datasets[0].data = item.value.getData()
      this.selectedItem = item
    }
  }

  isIntervalEmpty(): boolean {
    return !this.start || !this.end
  }

  areValuesEmpty(): boolean {
    return !this.calls && !this.attendances
  }

  getTitle() {
    const startDate = new Date( this.start ).toLocaleDateString('pt-Br')
    const endDate = new Date( this.end ).toLocaleDateString('pt-Br')
    return `${ this.selectedItem.label } entre ${ startDate } e ${ endDate }`
  }
}