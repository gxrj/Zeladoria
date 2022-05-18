import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePage } from './home.page';
import { CallListResolver } from '@resolvers/call/call-list.resolver';
import { AttendanceComponent } from './components/attendance/attendance.component';
import { CallComponent } from './components/call/call.component';
import { DutyListResolver } from '@resolvers/duty/duty-list.resolver';
import { AttendanceListResolver } from '@resolvers/attendance/attendance-list.resolver';
import { ReportComponent } from './components/report/report.component';
import { DutyComponent } from './components/duty/duty.component';
import { CategoryComponent } from './components/category/category.component';
import { DepartmentComponent } from './components/department/department.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserFormComponent } from './components/forms/user-form/user-form.component';

const routes: Routes = [
  {
    path: '',
    component: HomePage,
    children: 
    [
      {
        path: 'calls',
        component: CallComponent,
        resolve: { calls: CallListResolver },
        runGuardsAndResolvers: 'paramsOrQueryParamsChange'
      },
      {
        path: 'attendances',
        component: AttendanceComponent,
        resolve: { attendances: AttendanceListResolver },
        runGuardsAndResolvers: 'paramsOrQueryParamsChange'
      },
      {
        path: 'categories',
        component: CategoryComponent
      },
      {
        path: 'departments',
        component: DepartmentComponent
      },
      {
        path: 'duties',
        component: DutyComponent
      },
      {
        path: 'reports',
        component: ReportComponent
      },
      {
        path: 'user-form',
        component: UserFormComponent
      },
      {
        path: 'user-list',
        component: UserListComponent
      }
    ],
    resolve: { duties: DutyListResolver }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomePageRoutingModule {}