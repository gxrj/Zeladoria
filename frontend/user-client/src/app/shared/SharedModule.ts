import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CardButtonComponent } from '@components/card-button/card-button.component';

@NgModule( { 
    declarations: [ 
        CardButtonComponent 
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
        CardButtonComponent 
    ]
} )
export class SharedModule {}