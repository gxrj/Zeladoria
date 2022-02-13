import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared/SharedModule';

import { StartPageComponent } from '@pages/start-page/start-page.component';

@NgModule( { 
    declarations: [ StartPageComponent ],
    imports: [ SharedModule ],
    exports: [ StartPageComponent ]
} )
export class StartPageModule {}