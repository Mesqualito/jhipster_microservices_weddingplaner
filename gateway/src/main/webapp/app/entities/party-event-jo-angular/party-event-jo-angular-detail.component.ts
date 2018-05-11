import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { PartyEventJoAngular } from './party-event-jo-angular.model';
import { PartyEventJoAngularService } from './party-event-jo-angular.service';

@Component({
    selector: 'jhi-party-event-jo-angular-detail',
    templateUrl: './party-event-jo-angular-detail.component.html'
})
export class PartyEventJoAngularDetailComponent implements OnInit, OnDestroy {

    partyEvent: PartyEventJoAngular;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private partyEventService: PartyEventJoAngularService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPartyEvents();
    }

    load(id) {
        this.partyEventService.find(id)
            .subscribe((partyEventResponse: HttpResponse<PartyEventJoAngular>) => {
                this.partyEvent = partyEventResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPartyEvents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'partyEventListModification',
            (response) => this.load(this.partyEvent.id)
        );
    }
}
