import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'redirection',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: () => import( '@pages/home/home.module' ).then( m => m.HomePageModule )
  },
  {
    path: 'redirection',
    loadChildren: () => import( '@pages/oauth-redirect/oauth-redirect.module' )
                        .then( m => m.OauthRedirectPageModule )
  }
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
