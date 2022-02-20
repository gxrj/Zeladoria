import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomePageComponent } from '@pages/home-page/home-page.component';
import { StartPageComponent } from '@pages/start-page/start-page.component';
import { OAuth2RedirectionPageComponent } from '@pages/o-auth2-redirection-page/o-auth2-redirection-page.component';
import { QuizPageComponent } from '@pages/quiz-page/quiz-page.component';

const routes: Routes = [
  {
    path:'',
    redirectTo: 'start',
    pathMatch: 'full'
  },
  {
    path: 'start',
    component: StartPageComponent,
    loadChildren: () => import( '@pages/start-page/start-page.module' ).then( m => m.StartPageModule )
  },
  {
    path: 'redirection',
    component: OAuth2RedirectionPageComponent,
    loadChildren: () => 
                      import( '@pages/o-auth2-redirection-page/o-auth2-redirection-page.module' )
                      .then( m => m.OAuth2RedirectionPageModule )
  },
  {
    path: 'home',
    component: HomePageComponent,
    loadChildren: () => import( '@pages/home-page/home-page.module' ).then( m => m.HomePageModule )
  },
  {
    path: 'service-quiz',
    component: QuizPageComponent,
    loadChildren: () => import( '@pages/quiz-page/quiz-page.module' ).then( m => m.QuizPageModule )
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }