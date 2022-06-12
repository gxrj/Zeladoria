import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { UserInfoResolver } from '@resolvers/user-info/user-info.resolver';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'start',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: () => import( '@pages/home/home.module' ).then( m => m.HomePageModule ),
    resolve: { account: UserInfoResolver }
  },
  {
    path: 'redirection',
    loadChildren: () => import( '@pages/oauth-redirect/oauth-redirect.module' )
                        .then( m => m.OauthRedirectPageModule )
  },
  {
    path: 'start',
    loadChildren: () => import( '@pages/start/start.module' ).then( m => m.StartPageModule )
  }
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}