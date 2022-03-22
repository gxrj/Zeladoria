import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { StartPageRoutingModule } from './start-routing.module';

import { StartPage } from './start.page';
import { SharedModule } from '@app/shared/SharedModule';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SharedModule,
    StartPageRoutingModule
  ],
  declarations: [StartPage]
})
export class StartPageModule {}
