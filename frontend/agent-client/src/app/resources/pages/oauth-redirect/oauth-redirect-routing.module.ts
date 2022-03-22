import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { OauthRedirectPage } from './oauth-redirect.page';

const routes: Routes = [
  {
    path: '',
    component: OauthRedirectPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OauthRedirectPageRoutingModule {}
