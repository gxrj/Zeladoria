import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http'
import { AuthService } from '@services/auth/auth.service';
import { SharedModule } from './shared/SharedModule';
import { UserServiceComponent } from './shared/services/user/user-service/user-service.component';

@NgModule({
  declarations: [
    AppComponent,
    UserServiceComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SharedModule
  ],
  providers: [
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
