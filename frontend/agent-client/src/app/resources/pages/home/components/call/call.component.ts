import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-call',
  templateUrl: './call.component.html',
  styleUrls: ['./call.component.scss'],
})
export class CallComponent implements OnInit {

  titles = [ "Category", "District", "Date", "Author" ]

  content = [
    {
      category: "Test",
      district: "Downtown",
      date: "20/03/2022",
      author: "Anonymous"
    },
    {
      category: "Test",
      district: "Downtown",
      date: "20/03/2022",
      author: "Anonymous"
    },
    {
      category: "Test",
      district: "Downtown",
      date: "20/03/2022",
      author: "Anonymous"
    }
  ]

  constructor() { }

  ngOnInit() {}

}