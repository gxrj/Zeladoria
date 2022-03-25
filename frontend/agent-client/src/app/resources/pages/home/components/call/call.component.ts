import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CallService } from '@services/call/call.service';
import { ToastService } from '@services/toast/toast.service';
import Call from '@core/interfaces/call'

@Component({
  selector: 'app-call',
  templateUrl: './call.component.html',
  styleUrls: ['./call.component.scss'],
})
export class CallComponent implements OnInit {

  titles = [ 'Serviço', 'Bairro', 'Postagem', 'Autor' ]
  calls: any = null
  selectedCall: Call = null

  constructor( 
    private _toast: ToastService, 
    private _route: ActivatedRoute,
    private _callService: CallService ) { }

  ngOnInit() {
    this.calls = this._route.snapshot.data.calls.result
  }

  selectCall( call ) {
    this.selectedCall = call
    console.log( call )
  }

  return() {
    this.selectedCall = null
  }

  reload() {
    const user = sessionStorage.getItem( 'user' )

    if( !user ) {
      this._toast.displayMessage( 'Falha no carregamento, por favor reinicie sua sessão' )
      return null
    }

    this._callService.list( '/agent/calls/all', JSON.parse( user ) )
                      .subscribe(
                        resp =>  this.calls = resp.result,
                        error => this._toast
                                  .displayMessage( 
                                      `Falha no carregamento: ${ JSON.stringify( error.error ) }` )
                      )
  }
}