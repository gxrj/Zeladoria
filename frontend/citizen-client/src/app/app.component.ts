import { Component, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { App, URLOpenListenerEvent } from '@capacitor/app';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  constructor(
    private _router: Router,
    private _zone: NgZone ) {
      this.initApp()
    }
    /**Convert content internal identity into deep links */
    initApp() {
      App.addListener( 'appUrlOpen', ( event: URLOpenListenerEvent ) => {
        this._zone.run( () => {
          const path = event.url.split( 'macae.com' ).pop()
          if( path )
            this._router.navigateByUrl( path )
        } )
      } )
    }
}