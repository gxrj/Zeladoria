import { NgModule } from "@angular/core";
import { SharedModule } from "@app/shared/SharedModule";
import { QuizPageComponent } from "./quiz-page.component";

@NgModule( {
    declarations: [ QuizPageComponent ],
    imports: [ SharedModule ],
    exports: [ QuizPageComponent ]
} )
export class QuizPageModule {}