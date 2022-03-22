import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NavbarComponent } from '@components/navbar/navbar.component';
import { SideMenuComponent } from '@components/side-menu/side-menu.component';
import { SpinnerComponent } from '@components/spinner/spinner.component';


@NgModule( { 
    declarations: [ 
        NavbarComponent, 
        SideMenuComponent,
        SpinnerComponent 
    ],
    imports: [ 
        CommonModule,
        FormsModule,
        ReactiveFormsModule 
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NavbarComponent,
        SideMenuComponent,
        SpinnerComponent
    ]
} )
export class SharedModule {}