import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePage } from './home.page';
import { CallResolver } from '@resolvers/call/call.resolver';
import { AttendanceComponent } from './components/attendance/attendance.component';
import { CallComponent } from './components/call/call.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { DutyResolver } from '@resolvers/duty/duty.resolver';

const routes: Routes = [
  {
    path: '',
    component: HomePage,
    children: 
    [
      {
        path: 'calls',
        component: CallComponent,/*
        resolve: { calls: CallResolver, duties: DutyResolver },
        runGuardsAndResolvers: 'always'*/
      },
      {
        path: 'attendances',
        component: AttendanceComponent
      },
      {
        path: 'feedbacks',
        component: FeedbackComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomePageRoutingModule {}