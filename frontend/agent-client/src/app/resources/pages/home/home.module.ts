import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { HomePageRoutingModule } from './home-routing.module';

import { HomePage } from './home.page';
import { SharedModule } from '@shared/shared.module';
import { CallComponent } from './components/call/call.component';
import { AttendanceComponent } from './components/attendance/attendance.component';
import { CallFormComponent } from './components/forms/call-form/call-form.component';
import { AttendanceCreationFormComponent } from './components/forms/attendance-creation-form/attendance-creation-form.component';
import { AttendanceViewFormComponent } from './components/forms/attendance-view-form/attendance-view-form.component';


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
    AttendanceComponent,
    CallFormComponent,
    AttendanceCreationFormComponent,
    AttendanceViewFormComponent
  ]
})
export class HomePageModule {}