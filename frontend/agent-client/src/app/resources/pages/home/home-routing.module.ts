import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePage } from './home.page';
import { CallListResolver } from '@resolvers/call/call-list.resolver';
import { AttendanceComponent } from './components/attendance/attendance.component';
import { CallComponent } from './components/call/call.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { DutyListResolver } from '@resolvers/duty/duty-list.resolver';

const routes: Routes = [
  {
    path: '',
    component: HomePage,
    children: 
    [
      {
        path: 'calls',
        component: CallComponent,
        resolve: { calls: CallListResolver, duties: DutyListResolver },
        runGuardsAndResolvers: 'always'
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