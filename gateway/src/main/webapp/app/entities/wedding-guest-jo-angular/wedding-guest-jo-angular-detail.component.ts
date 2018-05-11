import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingGuestJoAngular } from './wedding-guest-jo-angular.model';
import { WeddingGuestJoAngularService } from './wedding-guest-jo-angular.service';

@Component({
    selector: 'jhi-wedding-guest-jo-angular-detail',
    templateUrl: './wedding-guest-jo-angular-detail.component.html'
})
export class WeddingGuestJoAngularDetailComponent implements OnInit, OnDestroy {

    weddingGuest: WeddingGuestJoAngular;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private weddingGuestService: WeddingGuestJoAngularService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWeddingGuests();
    }

    load(id) {
        this.weddingGuestService.find(id)
            .subscribe((weddingGuestResponse: HttpResponse<WeddingGuestJoAngular>) => {
                this.weddingGuest = weddingGuestResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWeddingGuests() {
        this.eventSubscriber = this.eventManager.subscribe(
            'weddingGuestListModification',
            (response) => this.load(this.weddingGuest.id)
        );
    }
}
