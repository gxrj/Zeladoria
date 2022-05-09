import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CallPageRoutingModule } from './call-routing.module';
import { CallPage } from './call.page';
import { SharedModule } from '@app/shared/SharedModule';

@NgModule({
  imports: [
    CommonModule,
    CallPageRoutingModule,
    SharedModule
  ],
  declarations: [ CallPage ]
})
export class CallPageModule {}