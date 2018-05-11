import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { PartyFoodJoAngular } from './party-food-jo-angular.model';
import { PartyFoodJoAngularPopupService } from './party-food-jo-angular-popup.service';
import { PartyFoodJoAngularService } from './party-food-jo-angular.service';
import { AppUserJoAngular, AppUserJoAngularService } from '../app-user-jo-angular';
import { WeddingHostJoAngular, WeddingHostJoAngularService } from '../wedding-host-jo-angular';

@Component({
    selector: 'jhi-party-food-jo-angular-dialog',
    templateUrl: './party-food-jo-angular-dialog.component.html'
})
export class PartyFoodJoAngularDialogComponent implements OnInit {

    partyFood: PartyFoodJoAngular;
    isSaving: boolean;

    appusers: AppUserJoAngular[];

    weddinghosts: WeddingHostJoAngular[];
    foodBestServeTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private partyFoodService: PartyFoodJoAngularService,
        private appUserService: AppUserJoAngularService,
        private weddingHostService: WeddingHostJoAngularService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.appUserService.query()
            .subscribe((res: HttpResponse<AppUserJoAngular[]>) => { this.appusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.weddingHostService.query()
            .subscribe((res: HttpResponse<WeddingHostJoAngular[]>) => { this.weddinghosts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.partyFood.id !== undefined) {
            this.subscribeToSaveResponse(
                this.partyFoodService.update(this.partyFood));
        } else {
            this.subscribeToSaveResponse(
                this.partyFoodService.create(this.partyFood));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PartyFoodJoAngular>>) {
        result.subscribe((res: HttpResponse<PartyFoodJoAngular>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PartyFoodJoAngular) {
        this.eventManager.broadcast({ name: 'partyFoodListModification', content: 'OK'});
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

    trackWeddingHostById(index: number, item: WeddingHostJoAngular) {
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
    selector: 'jhi-party-food-jo-angular-popup',
    template: ''
})
export class PartyFoodJoAngularPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partyFoodPopupService: PartyFoodJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.partyFoodPopupService
                    .open(PartyFoodJoAngularDialogComponent as Component, params['id']);
            } else {
                this.partyFoodPopupService
                    .open(PartyFoodJoAngularDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
