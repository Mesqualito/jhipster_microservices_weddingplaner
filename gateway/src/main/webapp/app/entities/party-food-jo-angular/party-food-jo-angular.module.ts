import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    PartyFoodJoAngularService,
    PartyFoodJoAngularPopupService,
    PartyFoodJoAngularComponent,
    PartyFoodJoAngularDetailComponent,
    PartyFoodJoAngularDialogComponent,
    PartyFoodJoAngularPopupComponent,
    PartyFoodJoAngularDeletePopupComponent,
    PartyFoodJoAngularDeleteDialogComponent,
    partyFoodRoute,
    partyFoodPopupRoute,
    PartyFoodJoAngularResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...partyFoodRoute,
    ...partyFoodPopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PartyFoodJoAngularComponent,
        PartyFoodJoAngularDetailComponent,
        PartyFoodJoAngularDialogComponent,
        PartyFoodJoAngularDeleteDialogComponent,
        PartyFoodJoAngularPopupComponent,
        PartyFoodJoAngularDeletePopupComponent,
    ],
    entryComponents: [
        PartyFoodJoAngularComponent,
        PartyFoodJoAngularDialogComponent,
        PartyFoodJoAngularPopupComponent,
        PartyFoodJoAngularDeleteDialogComponent,
        PartyFoodJoAngularDeletePopupComponent,
    ],
    providers: [
        PartyFoodJoAngularService,
        PartyFoodJoAngularPopupService,
        PartyFoodJoAngularResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerPartyFoodJoAngularModule {}
