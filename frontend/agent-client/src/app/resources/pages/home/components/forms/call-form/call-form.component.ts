import { Component, Input, OnInit } from '@angular/core';

import Call from '@core/interfaces/call';
import Duty from '@core/interfaces/duty';
import { Message } from '@core/interfaces/message';
import { ModalController } from '@ionic/angular';
import { CallService } from '@services/call/call.service';
import { FileService } from '@services/file/file.service';
import { ToastService } from '@services/toast/toast.service';
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

  tempDuty: any
  tempDestination: any
  editDuty: boolean = false
  editDestination: boolean = false
  isPrank: boolean = false
  files: File[]

  constructor( 
    private _toast: ToastService,
    private _modal: ModalController,
    private _callService: CallService,
    private _fileService: FileService ) { }

  ngOnInit() {
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
    console.log( this.call.images );
    
    this.openModal()
  }

  forward() {
    if( this.editDuty )
      this.call.duty = this.tempDuty

    if( this.editDestination )
      this.call.destination.name = this.tempDestination

    this.answer()
  }

  delete() {
    this._callService.delete( '/agent/calls/deletion', this.call )
    .subscribe(
      ( res: Message ) => { this._toast.displayMessage( res?.message ) },
      error => {
        this._toast.displayMessage( 
          `Falha na gravação: ${ JSON.stringify( error?.message ) }` )
      }
    )
  }

  async openModal() {
    const modal = await this._modal.create( {
      component: AttendanceFormComponent,
      cssClass: 'default-modal',
      componentProps: { call: this.call, toForward: this.editDestination }
    } )
    return await modal.present();
  }

  checkAnonymous( email: string ):boolean { return email === 'anonimo@fiscaliza.com' }

  displayImgTable(): boolean {
    return this.call.images && this.call.images.length && this.call.images.length > 0
  }

  loadFile( filename: string ) {
    this._fileService
          .loadAllCallImgFiles( `/authenticated/call/files?${ filename }`, this.call )
            .subscribe( 
              ( res ) => { 
                this.renderFile( res )
              },
              err => { 
                this._toast.displayMessage( 'Falha no carregamento' )
                console.log( err )
              }
            )
  }

  loadAllFiles() {
    this._fileService
          .loadAllCallImgFiles( '/authenticated/call/files/zip', this.call )
            .subscribe( 
              ( res ) => { 
                this.downloadZip( res )
              },
              err => { 
                this._toast.displayMessage( 'Falha no carregamento' )
                console.log( err )
              }
            )
  }

  renderFile( file: File ) {
    console.log( file )
    //TODO
  }

  downloadZip( data: Blob ) {

    if( data.size ) {
      const file = new File( [data], 'images.zip', { type: 'application/zip' } )
      const url = URL.createObjectURL( file )
      window.open( url )
    }
    else
      this._toast.displayMessage( 'Não há imagens' )
  }
}