import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { CategoryResolver } from '@resolvers/category/category.resolver';
import { CallHistoryResolver } from './shared/resolvers/call-history/call-history.resolver';
import { DistrictResolver } from './shared/resolvers/district/district.resolver';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'start',
    pathMatch: 'full'
  },
  {
    path: 'start',
    loadChildren: () => import( '@pages/start/start.module' ).then( m => m.StartPageModule )
  },
  {
    path: 'home',
    loadChildren: () => import( '@pages/home/home.module' ).then( m => m.HomePageModule ),
    resolve: { categories: CategoryResolver }
  },
  {
    path: 'redirection',
    loadChildren: () => import( '@pages/oauth-redirect/oauth-redirect.module' )
                        .then( m => m.OauthRedirectPageModule )
  },
  {
    path: 'call',
    loadChildren: () => import( '@pages/call/call.module' ).then( m => m.CallPageModule ),
    resolve: { districts: DistrictResolver }
  },
  {
    path: 'history',
    loadChildren: () => import( '@pages/call-history/call-history.module' )
                              .then( m => m.CallHistoryPageModule ),
    resolve: { history: CallHistoryResolver }
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot( routes, { preloadingStrategy: PreloadAllModules } )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }