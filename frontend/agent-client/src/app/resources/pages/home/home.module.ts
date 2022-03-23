import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { HomePageRoutingModule } from './home-routing.module';

import { HomePage } from './home.page';
import { SharedModule } from '@app/shared/SharedModule';
import { CallComponent } from './components/call/call.component';
import { ForwardedCallComponent } from './components/forwarded-call/forwarded-call.component';
import { AttendanceComponent } from './components/attendance/attendance.component';
import { FeedbackComponent } from './components/feedback/feedback.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SharedModule,
    HomePageRoutingModule
  ],
  declarations: [ 
    HomePage,
    CallComponent,
    FeedbackComponent,
    AttendanceComponent,
    ForwardedCallComponent
  ]
})
export class HomePageModule {}