import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { PartyEventJoAngular } from './party-event-jo-angular.model';
import { PartyEventJoAngularPopupService } from './party-event-jo-angular-popup.service';
import { PartyEventJoAngularService } from './party-event-jo-angular.service';
import { AppUserJoAngular, AppUserJoAngularService } from '../app-user-jo-angular';

@Component({
    selector: 'jhi-party-event-jo-angular-dialog',
    templateUrl: './party-event-jo-angular-dialog.component.html'
})
export class PartyEventJoAngularDialogComponent implements OnInit {

    partyEvent: PartyEventJoAngular;
    isSaving: boolean;

    appusers: AppUserJoAngular[];
    eventStartTimeDp: any;
    eventEndTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private partyEventService: PartyEventJoAngularService,
        private appUserService: AppUserJoAngularService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.appUserService.query()
            .subscribe((res: HttpResponse<AppUserJoAngular[]>) => { this.appusers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.partyEvent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.partyEventService.update(this.partyEvent));
        } else {
            this.subscribeToSaveResponse(
                this.partyEventService.create(this.partyEvent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PartyEventJoAngular>>) {
        result.subscribe((res: HttpResponse<PartyEventJoAngular>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PartyEventJoAngular) {
        this.eventManager.broadcast({ name: 'partyEventListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-party-event-jo-angular-popup',
    template: ''
})
export class PartyEventJoAngularPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partyEventPopupService: PartyEventJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.partyEventPopupService
                    .open(PartyEventJoAngularDialogComponent as Component, params['id']);
            } else {
                this.partyEventPopupService
                    .open(PartyEventJoAngularDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
