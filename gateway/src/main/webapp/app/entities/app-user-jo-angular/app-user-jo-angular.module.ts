import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    AppUserJoAngularService,
    AppUserJoAngularPopupService,
    AppUserJoAngularComponent,
    AppUserJoAngularDetailComponent,
    AppUserJoAngularDialogComponent,
    AppUserJoAngularPopupComponent,
    AppUserJoAngularDeletePopupComponent,
    AppUserJoAngularDeleteDialogComponent,
    appUserRoute,
    appUserPopupRoute,
} from './';

const ENTITY_STATES = [
    ...appUserRoute,
    ...appUserPopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AppUserJoAngularComponent,
        AppUserJoAngularDetailComponent,
        AppUserJoAngularDialogComponent,
        AppUserJoAngularDeleteDialogComponent,
        AppUserJoAngularPopupComponent,
        AppUserJoAngularDeletePopupComponent,
    ],
    entryComponents: [
        AppUserJoAngularComponent,
        AppUserJoAngularDialogComponent,
        AppUserJoAngularPopupComponent,
        AppUserJoAngularDeleteDialogComponent,
        AppUserJoAngularDeletePopupComponent,
    ],
    providers: [
        AppUserJoAngularService,
        AppUserJoAngularPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerAppUserJoAngularModule {}
