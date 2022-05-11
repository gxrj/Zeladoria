import { Component, Input, OnInit } from '@angular/core';
import { ImageViewerComponent } from '@components/image-viewer/image-viewer.component';
import { Attendance } from '@core/interfaces/attendance';

import Call from '@core/interfaces/call';
import Duty from '@core/interfaces/duty';
import { Message } from '@core/interfaces/message';
import { ModalController } from '@ionic/angular';
import { CallService } from '@services/call/call.service';
import { FileService } from '@services/file/file.service';
import { ToastService } from '@services/toast/toast.service';
import * as JSZip from 'jszip';
import { AttendanceFormComponent } from '../attendance-form/attendance-form.component';

@Component({
  selector: 'call-form',
  templateUrl: './call-form.component.html',
  styleUrls: ['./call-form.component.scss'],
})
export class CallFormComponent implements OnInit {

  @Input() call: Call
  @Input() duties: Array<Duty>
  @Input() deptList: Array<string>
  @Input() attendances: Array<Attendance>

  attendanceHeaders = [ 'Tipo', 'Protocolo', 'Responsável', 'Data', 'Ações' ]
  imageHeaders = [ 'Imagem', 'Ações' ]

  tempDuty: any
  tempDestination: any
  editDuty: boolean = false
  editDestination: boolean = false
  isPrank: boolean = false
  files: File[]
  displayFiles: boolean = false
  zipFile: File
  editionEnabled: boolean

  constructor( 
    private _toast: ToastService,
    private _modal: ModalController,
    private _callService: CallService,
    private _fileService: FileService ) { }

  ngOnInit() {
    this.checkEmbeddedFiles()

    if( this.displayFiles ) {
      this.loadZipFile()
    }
    this.tempDuty = this.call.duty
    this.tempDestination = this.call.destination.name
  }

  checkDestination() {
    if( this.tempDuty.department.name !== this.tempDestination ) {
      this.tempDestination = this.tempDuty.department.name
      this.editDestination = true
    }
    if( !this.editDuty ) {
      this.tempDestination = this.call.destination.name
      this.editDestination = false
    }
  }

  answer() { 
    this.openAttendanceCreationModal()
  }

  forward() {

    if( this.call.duty === this.tempDuty ) {
      this._toast
          .displayMessage( 'Secretaria de origem é igual a secretaria de destino, confira p destinatário' )
      return null
    }

    if( this.editDuty )
      this.call.duty = this.tempDuty

    if( this.editDestination )
      this.call.destination.name = this.tempDestination

    this.answer()
  }

  delete() {
    this._callService.delete( this.call )
    .subscribe(
      ( res: Message ) => { this._toast.displayMessage( res?.message ) },
      error => {
        this._toast.displayMessage( 
          `Falha na gravação: ${ JSON.stringify( error?.message ) }` )
      }
    )
  }

  async openAttendanceCreationModal() {
    const modal = await this._modal.create( {
      component: AttendanceFormComponent,
      cssClass: 'default-modal',
      componentProps: { call: this.call, toForward: this.editDestination }
    } )
    return await modal.present();
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

  setFormEditionMode() {
    this.editionEnabled = ![ 'Finalizada', 'Indeferida', 'Respondida' ].includes( this.call.status ) 
  }
  async openAttendanceDetailsModal( element: Attendance ) {     
    // const modal = this._modal.create( {
    //   component: AttendanceFormComponent,
    //   cssClass: 'default-modal',
    //   componentProps: { attendance: element, call: this.call }
    // } )
    // const result = await modal
    // return result.present()
  }
}