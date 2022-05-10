import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '@env/environment';

import { AuthService } from '@services/auth/auth.service';
import { UserService } from '@services/user/user.service';
import { ToastService } from '@services/toast/toast.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { SharedModule } from '@shared/SharedModule';
import { CallService } from '@services/call/call.service';
import { DistrictService } from '@services/district/district.service';
import { AuthInterceptor } from './core/interceptors/auth/auth.interceptor';

@NgModule({
  declarations: [ AppComponent ],
  entryComponents: [],
  imports: [
    BrowserModule,
    HttpClientModule, 
    IonicModule.forRoot(), 
    AppRoutingModule,
    SharedModule, 
    ServiceWorkerModule
      .register('ngsw-worker.js', {
                  enabled: environment.production,
                  // Register the ServiceWorker as soon as the app is stable
                  // or after 30 seconds (whichever comes first).
                  registrationStrategy: 'registerWhenStable:30000'
                })],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, 
    AuthService, 
    UserService,
    ToastService,
    CallService,
    DistrictService,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}