import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.scss'],
})
export class DataTableComponent implements OnInit {

  @Input() headerTitles: Array<string>
  @Input() width: number | null

  constructor() { }

  ngOnInit() {
    this.width = !this.width ? 100 : this.width
  }

}