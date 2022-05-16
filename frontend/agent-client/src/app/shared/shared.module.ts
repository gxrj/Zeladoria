import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SpinnerComponent } from '@components/spinner/spinner.component';
import { NavbarComponent } from '@components/navbar/navbar.component';
import { SideMenuComponent } from '@components/side-menu/side-menu.component';
import { RouterModule } from '@angular/router';
import { DataTableComponent } from '@components/data-table/data-table.component';
import { IonicModule } from '@ionic/angular';
import { TextEditorComponent } from '@components/text-editor/text-editor.component';
import { NgChartsModule } from 'ng2-charts';

@NgModule( { 
    declarations: [ 
        SpinnerComponent, 
        NavbarComponent, 
        SideMenuComponent,
        DataTableComponent,
        TextEditorComponent
    ],
    imports: [ 
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        FormsModule,
        IonicModule 
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        SpinnerComponent,
        NavbarComponent,
        SideMenuComponent,
        DataTableComponent,
        TextEditorComponent,
        NgChartsModule
    ]
} )
export class SharedModule {}