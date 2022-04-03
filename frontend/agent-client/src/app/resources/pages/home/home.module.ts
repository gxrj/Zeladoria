import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { HomePageRoutingModule } from './home-routing.module';

import { HomePage } from './home.page';
import { SharedModule } from '@shared/shared.module';
import { CallComponent } from './components/call/call.component';
import { AttendanceComponent } from './components/attendance/attendance.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { CallFormComponent } from './components/forms/call-form/call-form.component';
import { AttendanceFormComponent } from './components/forms/attendance-form/attendance-form.component';


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
    CallFormComponent,
    AttendanceFormComponent
  ]
})
export class HomePageModule {}