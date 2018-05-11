import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AppUserJoAngular } from './app-user-jo-angular.model';
import { AppUserJoAngularPopupService } from './app-user-jo-angular-popup.service';
import { AppUserJoAngularService } from './app-user-jo-angular.service';
import { WeddingGuestJoAngular, WeddingGuestJoAngularService } from '../wedding-guest-jo-angular';
import { WeddingHostJoAngular, WeddingHostJoAngularService } from '../wedding-host-jo-angular';
import { WeddingServiceJoAngular, WeddingServiceJoAngularService } from '../wedding-service-jo-angular';
import { PartyFoodJoAngular, PartyFoodJoAngularService } from '../party-food-jo-angular';
import { MessageJoAngular, MessageJoAngularService } from '../message-jo-angular';

@Component({
    selector: 'jhi-app-user-jo-angular-dialog',
    templateUrl: './app-user-jo-angular-dialog.component.html'
})
export class AppUserJoAngularDialogComponent implements OnInit {

    appUser: AppUserJoAngular;
    isSaving: boolean;

    weddingguests: WeddingGuestJoAngular[];

    weddinghosts: WeddingHostJoAngular[];

    weddingservices: WeddingServiceJoAngular[];

    partyfoods: PartyFoodJoAngular[];

    messages: MessageJoAngular[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private appUserService: AppUserJoAngularService,
        private weddingGuestService: WeddingGuestJoAngularService,
        private weddingHostService: WeddingHostJoAngularService,
        private weddingServiceService: WeddingServiceJoAngularService,
        private partyFoodService: PartyFoodJoAngularService,
        private messageService: MessageJoAngularService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.weddingGuestService.query()
            .subscribe((res: HttpResponse<WeddingGuestJoAngular[]>) => { this.weddingguests = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.weddingHostService.query()
            .subscribe((res: HttpResponse<WeddingHostJoAngular[]>) => { this.weddinghosts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.weddingServiceService.query()
            .subscribe((res: HttpResponse<WeddingServiceJoAngular[]>) => { this.weddingservices = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.partyFoodService.query()
            .subscribe((res: HttpResponse<PartyFoodJoAngular[]>) => { this.partyfoods = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.messageService.query()
            .subscribe((res: HttpResponse<MessageJoAngular[]>) => { this.messages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.appUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.appUserService.update(this.appUser));
        } else {
            this.subscribeToSaveResponse(
                this.appUserService.create(this.appUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AppUserJoAngular>>) {
        result.subscribe((res: HttpResponse<AppUserJoAngular>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AppUserJoAngular) {
        this.eventManager.broadcast({ name: 'appUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWeddingGuestById(index: number, item: WeddingGuestJoAngular) {
        return item.id;
    }

    trackWeddingHostById(index: number, item: WeddingHostJoAngular) {
        return item.id;
    }

    trackWeddingServiceById(index: number, item: WeddingServiceJoAngular) {
        return item.id;
    }

    trackPartyFoodById(index: number, item: PartyFoodJoAngular) {
        return item.id;
    }

    trackMessageById(index: number, item: MessageJoAngular) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-app-user-jo-angular-popup',
    template: ''
})
export class AppUserJoAngularPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appUserPopupService: AppUserJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.appUserPopupService
                    .open(AppUserJoAngularDialogComponent as Component, params['id']);
            } else {
                this.appUserPopupService
                    .open(AppUserJoAngularDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
