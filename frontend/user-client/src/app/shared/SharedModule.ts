import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CardButtonComponent } from '@components/card-button/card-button.component';
import { NavbarComponent } from '@components/navbar/navbar.component';

@NgModule( { 
    declarations: [ 
        CardButtonComponent,
        NavbarComponent 
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
        CardButtonComponent,
        NavbarComponent 
    ]
} )
export class SharedModule {}