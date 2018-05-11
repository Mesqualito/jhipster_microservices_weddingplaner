import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    WeddingHostJoAngularService,
    WeddingHostJoAngularPopupService,
    WeddingHostJoAngularComponent,
    WeddingHostJoAngularDetailComponent,
    WeddingHostJoAngularDialogComponent,
    WeddingHostJoAngularPopupComponent,
    WeddingHostJoAngularDeletePopupComponent,
    WeddingHostJoAngularDeleteDialogComponent,
    weddingHostRoute,
    weddingHostPopupRoute,
} from './';

const ENTITY_STATES = [
    ...weddingHostRoute,
    ...weddingHostPopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WeddingHostJoAngularComponent,
        WeddingHostJoAngularDetailComponent,
        WeddingHostJoAngularDialogComponent,
        WeddingHostJoAngularDeleteDialogComponent,
        WeddingHostJoAngularPopupComponent,
        WeddingHostJoAngularDeletePopupComponent,
    ],
    entryComponents: [
        WeddingHostJoAngularComponent,
        WeddingHostJoAngularDialogComponent,
        WeddingHostJoAngularPopupComponent,
        WeddingHostJoAngularDeleteDialogComponent,
        WeddingHostJoAngularDeletePopupComponent,
    ],
    providers: [
        WeddingHostJoAngularService,
        WeddingHostJoAngularPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerWeddingHostJoAngularModule {}
