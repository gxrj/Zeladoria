import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CallPageRoutingModule } from './call-routing.module';
import { CallPage } from './call.page';
import { SharedModule } from '@app/shared/SharedModule';
import { CallFormComponent } from './components/call-form/call-form.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CallPageRoutingModule,
    SharedModule
  ],
  declarations: [ CallPage, CallFormComponent ]
})
export class CallPageModule {}