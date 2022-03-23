import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { ServiceWorkerModule } from '@angular/service-worker';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { SharedModule } from './shared/SharedModule';
import { environment } from '@env/environment';
import { AuthService } from '@services/auth/auth.service';
import { TokenStorageService } from '@services/token-storage/token-storage.service';
import { ToastService } from '@services/toast/toast.service';
import { UserService } from '@services/user/user.service';
import { CallService } from '@services/call/call.service';

@NgModule( {
  declarations: [ AppComponent ],
  entryComponents: [],
  imports: [
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }), 
    BrowserModule,
    HttpClientModule,  
    IonicModule.forRoot(), 
    AppRoutingModule,
    SharedModule
   ],
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    AuthService,
    TokenStorageService,
    ToastService,
    UserService,
    CallService
  ],
  bootstrap: [ AppComponent ],
} )
export class AppModule {}