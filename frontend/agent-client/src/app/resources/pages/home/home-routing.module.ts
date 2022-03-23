import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AttendanceComponent } from './components/attendance/attendance.component';
import { CallComponent } from './components/call/call.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { ForwardedCallComponent } from './components/forwarded-call/forwarded-call.component';

import { HomePage } from './home.page';

const routes: Routes = [
  {
    path: '',
    component: HomePage,
    children: 
    [
      {
        path: 'calls',
        component: CallComponent
      },
      {
        path: 'forwarded-calls',
        component: ForwardedCallComponent
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