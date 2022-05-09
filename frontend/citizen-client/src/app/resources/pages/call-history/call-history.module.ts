import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared/SharedModule';

import { CallHistoryPageRoutingModule } from './call-history-routing.module';
import { CallHistoryPage } from './call-history.page';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    CallHistoryPageRoutingModule
  ],
  declarations: [CallHistoryPage]
})
export class CallHistoryPageModule {}
