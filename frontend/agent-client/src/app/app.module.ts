import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { ServiceWorkerModule } from '@angular/service-worker';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { SharedModule } from './shared/shared.module';
import { environment } from '@env/environment';
import { AuthService } from '@services/auth/auth.service';
import { TokenStorageService } from '@services/token-storage/token-storage.service';
import { ToastService } from '@services/toast/toast.service';
import { UserService } from '@services/user/user.service';
import { CallService } from '@services/call/call.service';
import { AttendanceService } from '@services/attendance/attendance.service';
import { FileService } from '@services/file/file.service';

import { AuthInterceptor } from '@core/interceptors/auth/auth.interceptor';

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
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    AuthService,
    TokenStorageService,
    ToastService,
    UserService,
    CallService,
    FileService,
    AttendanceService
  ],
  bootstrap: [ AppComponent ],
} )
export class AppModule {}