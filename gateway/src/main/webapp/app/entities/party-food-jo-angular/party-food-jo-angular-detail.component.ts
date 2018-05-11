import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { PartyFoodJoAngular } from './party-food-jo-angular.model';
import { PartyFoodJoAngularService } from './party-food-jo-angular.service';

@Component({
    selector: 'jhi-party-food-jo-angular-detail',
    templateUrl: './party-food-jo-angular-detail.component.html'
})
export class PartyFoodJoAngularDetailComponent implements OnInit, OnDestroy {

    partyFood: PartyFoodJoAngular;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private partyFoodService: PartyFoodJoAngularService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPartyFoods();
    }

    load(id) {
        this.partyFoodService.find(id)
            .subscribe((partyFoodResponse: HttpResponse<PartyFoodJoAngular>) => {
                this.partyFood = partyFoodResponse.body;
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

    registerChangeInPartyFoods() {
        this.eventSubscriber = this.eventManager.subscribe(
            'partyFoodListModification',
            (response) => this.load(this.partyFood.id)
        );
    }
}
