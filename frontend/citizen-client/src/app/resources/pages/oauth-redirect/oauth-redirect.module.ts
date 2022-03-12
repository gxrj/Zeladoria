import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { OauthRedirectPageRoutingModule } from './oauth-redirect-routing.module';

import { OauthRedirectPage } from './oauth-redirect.page';
import { SharedModule } from '@app/shared/SharedModule';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    OauthRedirectPageRoutingModule,
    SharedModule
  ],
  declarations: [OauthRedirectPage]
})
export class OauthRedirectPageModule {}
