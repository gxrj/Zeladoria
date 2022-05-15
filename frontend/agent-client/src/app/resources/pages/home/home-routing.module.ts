import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePage } from './home.page';
import { CallListResolver } from '@resolvers/call/call-list.resolver';
import { AttendanceComponent } from './components/attendance/attendance.component';
import { CallComponent } from './components/call/call.component';
import { DutyListResolver } from '@resolvers/duty/duty-list.resolver';
import { AttendanceListResolver } from '@resolvers/attendance/attendance-list.resolver';
import { ReportComponent } from './components/report/report.component';

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
        path: 'reports',
        component: ReportComponent
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