import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WeddingHostJoAngular } from './wedding-host-jo-angular.model';
import { WeddingHostJoAngularPopupService } from './wedding-host-jo-angular-popup.service';
import { WeddingHostJoAngularService } from './wedding-host-jo-angular.service';
import { AppUserJoAngular, AppUserJoAngularService } from '../app-user-jo-angular';
import { PartyFoodJoAngular, PartyFoodJoAngularService } from '../party-food-jo-angular';

@Component({
    selector: 'jhi-wedding-host-jo-angular-dialog',
    templateUrl: './wedding-host-jo-angular-dialog.component.html'
})
export class WeddingHostJoAngularDialogComponent implements OnInit {

    weddingHost: WeddingHostJoAngular;
    isSaving: boolean;

    appusers: AppUserJoAngular[];

    foodproposalaccepthosts: PartyFoodJoAngular[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private weddingHostService: WeddingHostJoAngularService,
        private appUserService: AppUserJoAngularService,
        private partyFoodService: PartyFoodJoAngularService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.appUserService
            .query({filter: 'weddinghost-is-null'})
            .subscribe((res: HttpResponse<AppUserJoAngular[]>) => {
                if (!this.weddingHost.appUserId) {
                    this.appusers = res.body;
                } else {
                    this.appUserService
                        .find(this.weddingHost.appUserId)
                        .subscribe((subRes: HttpResponse<AppUserJoAngular>) => {
                            this.appusers = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.partyFoodService
            .query({filter: 'acceptedbyhost-is-null'})
            .subscribe((res: HttpResponse<PartyFoodJoAngular[]>) => {
                if (!this.weddingHost.foodProposalAcceptHostId) {
                    this.foodproposalaccepthosts = res.body;
                } else {
                    this.partyFoodService
                        .find(this.weddingHost.foodProposalAcceptHostId)
                        .subscribe((subRes: HttpResponse<PartyFoodJoAngular>) => {
                            this.foodproposalaccepthosts = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.weddingHost.id !== undefined) {
            this.subscribeToSaveResponse(
                this.weddingHostService.update(this.weddingHost));
        } else {
            this.subscribeToSaveResponse(
                this.weddingHostService.create(this.weddingHost));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<WeddingHostJoAngular>>) {
        result.subscribe((res: HttpResponse<WeddingHostJoAngular>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: WeddingHostJoAngular) {
        this.eventManager.broadcast({ name: 'weddingHostListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAppUserById(index: number, item: AppUserJoAngular) {
        return item.id;
    }

    trackPartyFoodById(index: number, item: PartyFoodJoAngular) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-wedding-host-jo-angular-popup',
    template: ''
})
export class WeddingHostJoAngularPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weddingHostPopupService: WeddingHostJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.weddingHostPopupService
                    .open(WeddingHostJoAngularDialogComponent as Component, params['id']);
            } else {
                this.weddingHostPopupService
                    .open(WeddingHostJoAngularDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
