import { Component, Input, OnInit } from '@angular/core';
import { ImageViewerComponent } from '@components/image-viewer/image-viewer.component';
import { Attendance } from '@core/interfaces/attendance';
import Call from '@core/interfaces/call';
import { ModalController } from '@ionic/angular';
import { FileService } from '@services/file/file.service';
import { ToastService } from '@services/toast/toast.service';
import * as JSZip from 'jszip';

@Component({
  selector: 'attendance-form',
  templateUrl: './attendance-form.component.html',
  styleUrls: ['./attendance-form.component.scss'],
})
export class AttendanceFormComponent implements OnInit {

  @Input() attendance: Attendance

  files: File[]
  zipFile: File
  displayFiles: boolean = false
  call: Call
  dateFormat = 'dd/MM/y'
  timeFormat = 'HH:mm'
  imageHeaders = [ 'Imagem', 'Ações' ]
  
  constructor(
    private _toast: ToastService,
    private _modal: ModalController,
    private _fileService: FileService ) { }

  ngOnInit() {
    this.call = this.attendance.call
    this.checkEmbeddedFiles()
    if( this.displayFiles ) {
      this.loadZipFile()
    }
  }

  checkAnonymous( email: string ):boolean { return email === 'anonimo@fiscaliza.com' }

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

  downloadZip() {
    if( this.zipFile && this.zipFile.size ) {
      const url = URL.createObjectURL( this.zipFile )
      window.open( url )
    }
    else
      this._toast.displayMessage( 'Não há imagens' )
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

  downloadFile( filename: string ) {
    const file = this.files.filter( el => el.name === filename )[0]
    const url = URL.createObjectURL( file )
    window.open( url )
  }
}