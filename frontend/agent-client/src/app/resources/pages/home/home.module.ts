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
import { 
  AttendanceFormComponent 
} from './components/forms/attendance-form/attendance-form.component';
import { 
  AttendanceViewModalComponent 
} from './components/forms/attendance-view-modal/attendance-view-modal.component';
import { 
  AttendanceCreationModalComponent 
} from './components/forms/attendance-creation-modal/attendance-creation-modal.component';
import { ReportComponent } from './components/report/report.component';
import { ChartComponent } from './components/chart/chart.component';
import { DepartmentComponent } from './components/department/department.component';
import { DutyComponent } from './components/duty/duty.component';
import { CategoryComponent } from './components/category/category.component';
import { UserFormComponent } from './components/forms/user-form/user-form.component';
import { UserListComponent } from './components/user-list/user-list.component';

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
    DutyComponent,
    CallComponent,
    ChartComponent,
    ReportComponent,
    UserListComponent, 
    UserFormComponent,
    CallFormComponent,
    CategoryComponent,
    DepartmentComponent,  
    AttendanceComponent,
    AttendanceFormComponent,
    AttendanceViewModalComponent,
    AttendanceCreationModalComponent
  ]
})
export class HomePageModule {}