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
import { AttendanceCreationModalComponent } from './components/forms/attendance-creation-modal/attendance-creation-modal.component';
import { AttendanceViewModalComponent } from './components/forms/attendance-view-modal/attendance-view-modal.component';


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
    AttendanceCreationModalComponent,
    AttendanceViewModalComponent
  ]
})
export class HomePageModule {}