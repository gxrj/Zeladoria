import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import DatelabelsPlugin from 'chartjs-plugin-datalabels'
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss'],
})
export class ChartComponent implements OnInit {

  @ViewChild( BaseChartDirective ) chart: BaseChartDirective | undefined

  @Input() title: string = ''
  @Input() chartData: ChartData
  @Input() type: 'pie' | 'bar' = 'pie'
  @Input() legendPosition: 'bottom' | 'left' | 'top' | 'right' | 'center' = 'bottom'

  chartType: ChartType
  chartPlugins = [ DatelabelsPlugin ]

  chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    animation: false,
    plugins: {
      legend: {
        display: true,
        position: this.legendPosition
      }
    }
  }
  constructor() { }

  ngOnInit() {
    this.chartType = this.type
  }

}