import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';


@Component({
  selector: 'text-editor',
  templateUrl: './text-editor.component.html',
  styleUrls: ['./text-editor.component.scss'],
})
export class TextEditorComponent implements OnInit {

  @Input() text: string
  @Output() textChange = new EventEmitter<string>()

  constructor() { }

  ngOnInit() {}

  format( command: string ) {
    console.log( this.text )
    document.execCommand( command, false, '' )
  }
}