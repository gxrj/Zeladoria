import { Component, Input, OnInit } from '@angular/core';
import { Attendance } from '@app/core/interfaces/attendance';
import { ModalController } from '@ionic/angular';

import * as JSZip from 'jszip';

import Call from '@app/core/interfaces/call';
import { ImageViewerComponent } from '@app/shared/components/image-viewer/image-viewer.component';
import { FileService } from '@app/shared/services/file/file.service';
import { ToastService } from '@app/shared/services/toast/toast.service';
import { AttendanceFormComponent } from '@app/shared/components/attendance-form/attendance-form.component';

@Component({
  selector: 'call-history-form',
  templateUrl: './call-history-form.component.html',
  styleUrls: ['./call-history-form.component.scss'],
})
export class CallHistoryFormComponent implements OnInit {

  @Input() call: Call
  @Input() attendances: Attendance[]
  imageHeaders = [ 'Imagem', 'Ações' ]
  attendanceHeaders = [ 'Tipo', 'Protocolo', 'Responsável', 'Data', 'Ações' ]
  displayFiles: boolean
  zipFile: File
  files: File[]

  constructor( 
    private _modal: ModalController,
    private _toast: ToastService,
    private _fileService: FileService ) { }

  ngOnInit() {
    this.checkEmbeddedFiles()

    if( this.displayFiles ) { 
      this.loadZipFile()
    }
  }

  checkEmbeddedFiles() { 
    this.displayFiles = this.call.images && this.call.images.length && this.call.images.length > 0
  }

  loadZipFile() {
    this._fileService
          .loadAllCallImgFiles( this.call )
            .subscribe( 
              ( res ) => { 
                this.zipFile = new File( [res], 'images.zip', { type: 'application/zip' } )
                this.unzipFile( this.zipFile )
              },
              err => { 
                this._toast.displayMessage( 'Falha no carregamento de imagens' )
                console.log( err )
              }
            )
  }

  private unzipFile( zippedFile: File ) {
    this.files = []
    JSZip.loadAsync( zippedFile )
                        .then( 
                          ( zip ) => {
                            Object.values( zip.files )
                                  .forEach( el => el.async( 'blob' )
                                  .then( data => {
                                    const file = new File( [data], el.name, { type: 'image/ief' } )
                                    this.files.push( file )
                                   } ) )                    
                          } )
  }

  async renderFile( filename: string ) {

    const file = this.files.filter( el => el.name === filename )[0]

    const modal = await this._modal.create( {
      component: ImageViewerComponent,
      cssClass:'image-modal',
      componentProps: { image: file }
    } )
    return await modal.present()
  }

  async openModal( element: Attendance ) {     
    const modal = this._modal.create( {
      component: AttendanceFormComponent,
      cssClass: 'default-modal',
      componentProps: { attendance: element, call: this.call }
    } )
    const result = await modal
    return result.present()
  }
}