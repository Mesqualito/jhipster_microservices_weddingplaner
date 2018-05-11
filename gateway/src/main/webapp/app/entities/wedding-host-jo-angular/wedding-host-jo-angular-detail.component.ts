import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingHostJoAngular } from './wedding-host-jo-angular.model';
import { WeddingHostJoAngularService } from './wedding-host-jo-angular.service';

@Component({
    selector: 'jhi-wedding-host-jo-angular-detail',
    templateUrl: './wedding-host-jo-angular-detail.component.html'
})
export class WeddingHostJoAngularDetailComponent implements OnInit, OnDestroy {

    weddingHost: WeddingHostJoAngular;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private weddingHostService: WeddingHostJoAngularService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWeddingHosts();
    }

    load(id) {
        this.weddingHostService.find(id)
            .subscribe((weddingHostResponse: HttpResponse<WeddingHostJoAngular>) => {
                this.weddingHost = weddingHostResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWeddingHosts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'weddingHostListModification',
            (response) => this.load(this.weddingHost.id)
        );
    }
}
