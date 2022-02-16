import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared/SharedModule';

import { StartPageComponent } from '@pages/start-page/start-page.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';

@NgModule( { 
    declarations: [ 
        StartPageComponent,
        LoginFormComponent,
        RegisterFormComponent
    ],
    imports: [ SharedModule ],
    exports: [ StartPageComponent ]
} )
export class StartPageModule {}