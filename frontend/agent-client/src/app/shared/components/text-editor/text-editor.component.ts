import { Component, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'text-editor',
  templateUrl: './text-editor.component.html',
  styleUrls: ['./text-editor.component.scss'],
})
export class TextEditorComponent implements OnInit {

  @Input() @Output() text: string

  constructor() { }

  ngOnInit() {}

  format( command: string ) {
    document.execCommand( command, false, '' )
  }
}