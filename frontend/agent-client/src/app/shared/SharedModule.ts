
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SpinnerComponent } from '@components/spinner/spinner.component';
import { NavbarComponent } from '@components/navbar/navbar.component';
import { SideMenuComponent } from '@components/side-menu/side-menu.component';

@NgModule( { 
    declarations: [ 
        SpinnerComponent, 
        NavbarComponent, 
        SideMenuComponent ],
    imports: [ 
        CommonModule,
        FormsModule,
        ReactiveFormsModule 
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        SpinnerComponent,
        NavbarComponent,
        SideMenuComponent
    ]
} )
export class SharedModule {}