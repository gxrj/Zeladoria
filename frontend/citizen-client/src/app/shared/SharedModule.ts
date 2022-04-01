import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

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
        RouterModule,
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