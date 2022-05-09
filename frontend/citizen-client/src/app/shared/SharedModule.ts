import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';

import { NavbarComponent } from '@components/navbar/navbar.component';
import { SideMenuComponent } from '@components/side-menu/side-menu.component';
import { SpinnerComponent } from '@components/spinner/spinner.component';
import { CallFormComponent } from '@app/shared/components/call-form/call-form.component';
import { DataTableComponent } from './components/data-table/data-table.component';


@NgModule( { 
    declarations: [ 
        NavbarComponent, 
        SideMenuComponent,
        SpinnerComponent,
        DataTableComponent,
        CallFormComponent
    ],
    imports: [ 
        CommonModule,
        RouterModule,
        FormsModule,
        ReactiveFormsModule,
        IonicModule 
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NavbarComponent,
        SideMenuComponent,
        SpinnerComponent,
        DataTableComponent,
        CallFormComponent
    ]
} )
export class SharedModule {}