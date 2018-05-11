import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AddressJoAngular } from './address-jo-angular.model';
import { AddressJoAngularService } from './address-jo-angular.service';

@Component({
    selector: 'jhi-address-jo-angular-detail',
    templateUrl: './address-jo-angular-detail.component.html'
})
export class AddressJoAngularDetailComponent implements OnInit, OnDestroy {

    address: AddressJoAngular;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private addressService: AddressJoAngularService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAddresses();
    }

    load(id) {
        this.addressService.find(id)
            .subscribe((addressResponse: HttpResponse<AddressJoAngular>) => {
                this.address = addressResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'addressListModification',
            (response) => this.load(this.address.id)
        );
    }
}
