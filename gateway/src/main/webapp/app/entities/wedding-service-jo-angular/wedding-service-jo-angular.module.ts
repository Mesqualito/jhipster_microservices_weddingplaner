import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    WeddingServiceJoAngularService,
    WeddingServiceJoAngularPopupService,
    WeddingServiceJoAngularComponent,
    WeddingServiceJoAngularDetailComponent,
    WeddingServiceJoAngularDialogComponent,
    WeddingServiceJoAngularPopupComponent,
    WeddingServiceJoAngularDeletePopupComponent,
    WeddingServiceJoAngularDeleteDialogComponent,
    weddingServiceRoute,
    weddingServicePopupRoute,
} from './';

const ENTITY_STATES = [
    ...weddingServiceRoute,
    ...weddingServicePopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WeddingServiceJoAngularComponent,
        WeddingServiceJoAngularDetailComponent,
        WeddingServiceJoAngularDialogComponent,
        WeddingServiceJoAngularDeleteDialogComponent,
        WeddingServiceJoAngularPopupComponent,
        WeddingServiceJoAngularDeletePopupComponent,
    ],
    entryComponents: [
        WeddingServiceJoAngularComponent,
        WeddingServiceJoAngularDialogComponent,
        WeddingServiceJoAngularPopupComponent,
        WeddingServiceJoAngularDeleteDialogComponent,
        WeddingServiceJoAngularDeletePopupComponent,
    ],
    providers: [
        WeddingServiceJoAngularService,
        WeddingServiceJoAngularPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerWeddingServiceJoAngularModule {}
