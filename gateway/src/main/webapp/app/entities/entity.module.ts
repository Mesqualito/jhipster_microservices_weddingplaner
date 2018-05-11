import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WeddingplanerWeddingGuestJoAngularModule } from './wedding-guest-jo-angular/wedding-guest-jo-angular.module';
import { WeddingplanerWeddingServiceJoAngularModule } from './wedding-service-jo-angular/wedding-service-jo-angular.module';
import { WeddingplanerWeddingHostJoAngularModule } from './wedding-host-jo-angular/wedding-host-jo-angular.module';
import { WeddingplanerAddressJoAngularModule } from './address-jo-angular/address-jo-angular.module';
import { WeddingplanerPartyFoodJoAngularModule } from './party-food-jo-angular/party-food-jo-angular.module';
import { WeddingplanerPartyEventJoAngularModule } from './party-event-jo-angular/party-event-jo-angular.module';
import { WeddingplanerMessageJoAngularModule } from './message-jo-angular/message-jo-angular.module';
import { WeddingplanerAppUserJoAngularModule } from './app-user-jo-angular/app-user-jo-angular.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WeddingplanerWeddingGuestJoAngularModule,
        WeddingplanerWeddingServiceJoAngularModule,
        WeddingplanerWeddingHostJoAngularModule,
        WeddingplanerAddressJoAngularModule,
        WeddingplanerPartyFoodJoAngularModule,
        WeddingplanerPartyEventJoAngularModule,
        WeddingplanerMessageJoAngularModule,
        WeddingplanerAppUserJoAngularModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerEntityModule {}
