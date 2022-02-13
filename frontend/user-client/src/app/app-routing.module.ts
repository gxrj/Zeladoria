import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { StartPageComponent } from './pages/start-page/start-page.component';

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
    path: 'home',
    component: HomePageComponent,
    loadChildren: () => import( '@pages/home-page/home-page.module' ).then( m => m.HomePageModule )
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
