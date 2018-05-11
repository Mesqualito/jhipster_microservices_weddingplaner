import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    PartyEventJoAngularService,
    PartyEventJoAngularPopupService,
    PartyEventJoAngularComponent,
    PartyEventJoAngularDetailComponent,
    PartyEventJoAngularDialogComponent,
    PartyEventJoAngularPopupComponent,
    PartyEventJoAngularDeletePopupComponent,
    PartyEventJoAngularDeleteDialogComponent,
    partyEventRoute,
    partyEventPopupRoute,
    PartyEventJoAngularResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...partyEventRoute,
    ...partyEventPopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PartyEventJoAngularComponent,
        PartyEventJoAngularDetailComponent,
        PartyEventJoAngularDialogComponent,
        PartyEventJoAngularDeleteDialogComponent,
        PartyEventJoAngularPopupComponent,
        PartyEventJoAngularDeletePopupComponent,
    ],
    entryComponents: [
        PartyEventJoAngularComponent,
        PartyEventJoAngularDialogComponent,
        PartyEventJoAngularPopupComponent,
        PartyEventJoAngularDeleteDialogComponent,
        PartyEventJoAngularDeletePopupComponent,
    ],
    providers: [
        PartyEventJoAngularService,
        PartyEventJoAngularPopupService,
        PartyEventJoAngularResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerPartyEventJoAngularModule {}
