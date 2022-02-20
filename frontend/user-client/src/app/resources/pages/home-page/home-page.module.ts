import { NgModule } from "@angular/core";
import { SharedModule } from "@app/shared/SharedModule";

import { HomePageComponent } from './home-page.component';

@NgModule( {
    declarations:[ HomePageComponent ],
    imports: [ SharedModule ],
    exports: [ HomePageComponent ]
} )
export class HomePageModule {}