import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingServiceJoAngular } from './wedding-service-jo-angular.model';
import { WeddingServiceJoAngularService } from './wedding-service-jo-angular.service';

@Component({
    selector: 'jhi-wedding-service-jo-angular-detail',
    templateUrl: './wedding-service-jo-angular-detail.component.html'
})
export class WeddingServiceJoAngularDetailComponent implements OnInit, OnDestroy {

    weddingService: WeddingServiceJoAngular;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private weddingServiceService: WeddingServiceJoAngularService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWeddingServices();
    }

    load(id) {
        this.weddingServiceService.find(id)
            .subscribe((weddingServiceResponse: HttpResponse<WeddingServiceJoAngular>) => {
                this.weddingService = weddingServiceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWeddingServices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'weddingServiceListModification',
            (response) => this.load(this.weddingService.id)
        );
    }
}
