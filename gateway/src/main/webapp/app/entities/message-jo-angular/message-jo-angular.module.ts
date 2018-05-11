import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    MessageJoAngularService,
    MessageJoAngularPopupService,
    MessageJoAngularComponent,
    MessageJoAngularDetailComponent,
    MessageJoAngularDialogComponent,
    MessageJoAngularPopupComponent,
    MessageJoAngularDeletePopupComponent,
    MessageJoAngularDeleteDialogComponent,
    messageRoute,
    messagePopupRoute,
    MessageJoAngularResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...messageRoute,
    ...messagePopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MessageJoAngularComponent,
        MessageJoAngularDetailComponent,
        MessageJoAngularDialogComponent,
        MessageJoAngularDeleteDialogComponent,
        MessageJoAngularPopupComponent,
        MessageJoAngularDeletePopupComponent,
    ],
    entryComponents: [
        MessageJoAngularComponent,
        MessageJoAngularDialogComponent,
        MessageJoAngularPopupComponent,
        MessageJoAngularDeleteDialogComponent,
        MessageJoAngularDeletePopupComponent,
    ],
    providers: [
        MessageJoAngularService,
        MessageJoAngularPopupService,
        MessageJoAngularResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerMessageJoAngularModule {}
