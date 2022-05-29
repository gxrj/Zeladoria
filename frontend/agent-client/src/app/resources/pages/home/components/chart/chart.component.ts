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
  @Input() type: 'pie' | 'bar' | 'multi-bar' = 'pie'
  @Input() legendPosition: 'bottom' | 'left' | 'top' | 'right' | 'center' = 'top'
  @Input() total: number = 0

  chartType: ChartType
  chartPlugins = [ DatelabelsPlugin ]

  chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    animation: false,
    plugins: {
      legend: {
        display: true,
        position: this.legendPosition
      },
      datalabels: {
        formatter: ( val, ctx ) => {
          if( ctx.chart.data.labels && this.total ) {
            return (val*100/this.total).toFixed(2) + '%'
          }
        }
      }
    }
  }

  constructor() { }

  ngOnInit() {
    this.chartType = this.type === 'multi-bar' ? 'bar' : this.type
    this.setBarCharConfig()
  }

  setLabelPosition() {
    this.chartOptions.plugins.datalabels['anchor'] = 'end'
  }

  setBarCharConfig(){
    if( this.type === 'bar' ) {
      this.chartOptions['scales'] = { x: { }, y: this.total ? { max: this.total } : {} }
      this.chartOptions.plugins.legend.display = false
    }
  }
}