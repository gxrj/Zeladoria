import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared/SharedModule';

import { CallHistoryPageRoutingModule } from './call-history-routing.module';
import { CallHistoryPage } from './call-history.page';
import { CallHistoryFormComponent } from './components/call-history-form/call-history-form.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    CallHistoryPageRoutingModule
  ],
  declarations: [ CallHistoryPage, CallHistoryFormComponent ]
})
export class CallHistoryPageModule {}
