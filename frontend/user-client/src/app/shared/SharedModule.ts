import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { CardButtonComponent } from '@components/card-button/card-button.component';

@NgModule( { 
    declarations: [ 
        CardButtonComponent 
    ],
    imports: [ CommonModule ],
    exports: [ 
        CardButtonComponent 
    ]
} )
export class SharedModule {}