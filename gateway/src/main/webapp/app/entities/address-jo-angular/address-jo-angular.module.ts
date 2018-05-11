import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    AddressJoAngularService,
    AddressJoAngularPopupService,
    AddressJoAngularComponent,
    AddressJoAngularDetailComponent,
    AddressJoAngularDialogComponent,
    AddressJoAngularPopupComponent,
    AddressJoAngularDeletePopupComponent,
    AddressJoAngularDeleteDialogComponent,
    addressRoute,
    addressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...addressRoute,
    ...addressPopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AddressJoAngularComponent,
        AddressJoAngularDetailComponent,
        AddressJoAngularDialogComponent,
        AddressJoAngularDeleteDialogComponent,
        AddressJoAngularPopupComponent,
        AddressJoAngularDeletePopupComponent,
    ],
    entryComponents: [
        AddressJoAngularComponent,
        AddressJoAngularDialogComponent,
        AddressJoAngularPopupComponent,
        AddressJoAngularDeleteDialogComponent,
        AddressJoAngularDeletePopupComponent,
    ],
    providers: [
        AddressJoAngularService,
        AddressJoAngularPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerAddressJoAngularModule {}
