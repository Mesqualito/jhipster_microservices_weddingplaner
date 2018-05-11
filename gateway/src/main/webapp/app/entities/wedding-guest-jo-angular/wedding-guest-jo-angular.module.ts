import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    WeddingGuestJoAngularService,
    WeddingGuestJoAngularPopupService,
    WeddingGuestJoAngularComponent,
    WeddingGuestJoAngularDetailComponent,
    WeddingGuestJoAngularDialogComponent,
    WeddingGuestJoAngularPopupComponent,
    WeddingGuestJoAngularDeletePopupComponent,
    WeddingGuestJoAngularDeleteDialogComponent,
    weddingGuestRoute,
    weddingGuestPopupRoute,
} from './';

const ENTITY_STATES = [
    ...weddingGuestRoute,
    ...weddingGuestPopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WeddingGuestJoAngularComponent,
        WeddingGuestJoAngularDetailComponent,
        WeddingGuestJoAngularDialogComponent,
        WeddingGuestJoAngularDeleteDialogComponent,
        WeddingGuestJoAngularPopupComponent,
        WeddingGuestJoAngularDeletePopupComponent,
    ],
    entryComponents: [
        WeddingGuestJoAngularComponent,
        WeddingGuestJoAngularDialogComponent,
        WeddingGuestJoAngularPopupComponent,
        WeddingGuestJoAngularDeleteDialogComponent,
        WeddingGuestJoAngularDeletePopupComponent,
    ],
    providers: [
        WeddingGuestJoAngularService,
        WeddingGuestJoAngularPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerWeddingGuestJoAngularModule {}
