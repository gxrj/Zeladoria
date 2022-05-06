import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-image-viewer',
  templateUrl: './image-viewer.component.html',
  styleUrls: ['./image-viewer.component.scss'],
})
export class ImageViewerComponent implements OnInit {

  @Input() image: File
  url: string

  constructor( private _modal: ModalController ) { }

  ngOnInit() {
    let reader = new FileReader()

    reader.onload = ( event: any ) => {
      this.url = event.target.result
    }

    reader.readAsDataURL( this.image )
  }

  close(){ this._modal.dismiss() }
}
