import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import Call from '@core/interfaces/call';
import Duty from '@core/interfaces/duty';

import { CallService } from '@services/call/call.service';
import { UserService } from '@services/user/user.service';
import { ToastService } from '@app/shared/services/toast/toast.service';
import { Router } from '@angular/router';


@Component({
  selector: 'new-call-form',
  templateUrl: './new-call-form.component.html',
  styleUrls: ['./new-call-form.component.scss'],
})
export class NewCallFormComponent implements OnInit {

  @Input() selectedDuty: Duty | null
  @Input() districtList: any

  form: FormGroup
  call: Call
  images: File[]
  editionMode: boolean = false
  authenticated: boolean = false

  constructor( 
    private _fb: FormBuilder,
    private _router: Router,
    private _userService: UserService,
    private _callService: CallService,
    public _toastService: ToastService ) {

      this.form = this._fb.group( {
        dutyDescription:  [ '' ,Validators.required ],
        zipCode: [ '' ],
        district: [ '' ],
        street: [ '' ],
        description: [ '' ]
      } )
  }

  ngOnInit() {
    const duty = sessionStorage.getItem( 'selected-duty' )
    this.authenticated = sessionStorage.getItem( 'token' ) ? true : false

    if( !duty ) {
      this.form.patchValue( { dutyDescription: this.selectedDuty?.description }  )
      sessionStorage.setItem( 'selected-duty', JSON.stringify( this.selectedDuty ) )
    }
    else{
      this.selectedDuty = JSON.parse( duty )
      this.form.patchValue( { dutyDescription: this.selectedDuty.description } )
    }
  }

  selectFiles( event ) {
    const inputElement = event.target
    this.images = inputElement.files

    if( this.images.length > 0 )
      this.images = Object.values( this.images )
                          .filter( file => file.size <= 200000 )

    if( this.images.length > 3 ) {
      inputElement.value = ''
      this._toastService.displayMessage( "A ocorrência pode ter máximo 3 imagens" )
    }
  }

  sendData(): void {

    let form = new FormData()
    this.prepareCall()
    form.append( 'call', JSON.stringify( this.call ) )

    if( this.images )
        this.images
            .forEach( image => 
              form.append( 'files', image, image.name ) )

    this._callService.create( form )
                    .subscribe( 
                      res => {
                        this._toastService.displayMessage( res?.message )
                        this.call = res.call
                        this.editionMode = true

                        if( !this.authenticated )
                          this._router.navigateByUrl( '/home' ) 
                      },
                      error => this._toastService.displayMessage( error?.message )
                    )
  }

  private prepareCall() {

    let email = this._userService.getUserEmailFromToken()

    if( email == undefined || !email ) email = "anonimo@fiscaliza.com"
     
    const values = { ...this.form.value }
    this.call = {
                  duty: {
                    description: this.selectedDuty.description,
                    department: this.selectedDuty.department
                  },
                  description: values.description,
                  images: this.getFileNames(),
                  address: {
                    longitude: null,
                    latitude: null,
                    zip_code: values.zipCode,
                    public_place: values.street,
                    district: values.district,
                    reference: values.reference
                  },
                  author: { 
                    email: email 
                  },
                  status:"Em andamento"
                }
  }

  private getFileNames(): string[] {
    if( this.images )
      return this.images.map( file => file.name )
    else
      return []
  }

  ngOnDestroy() {
    sessionStorage.removeItem( 'duty-description' )
  }
}