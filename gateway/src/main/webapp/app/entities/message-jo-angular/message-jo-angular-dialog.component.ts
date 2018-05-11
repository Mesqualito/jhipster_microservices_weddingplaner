import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MessageJoAngular } from './message-jo-angular.model';
import { MessageJoAngularPopupService } from './message-jo-angular-popup.service';
import { MessageJoAngularService } from './message-jo-angular.service';
import { AppUserJoAngular, AppUserJoAngularService } from '../app-user-jo-angular';

@Component({
    selector: 'jhi-message-jo-angular-dialog',
    templateUrl: './message-jo-angular-dialog.component.html'
})
export class MessageJoAngularDialogComponent implements OnInit {

    message: MessageJoAngular;
    isSaving: boolean;

    appusers: AppUserJoAngular[];
    messageValidFromDp: any;
    messageValidUntilDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private messageService: MessageJoAngularService,
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
        if (this.message.id !== undefined) {
            this.subscribeToSaveResponse(
                this.messageService.update(this.message));
        } else {
            this.subscribeToSaveResponse(
                this.messageService.create(this.message));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MessageJoAngular>>) {
        result.subscribe((res: HttpResponse<MessageJoAngular>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MessageJoAngular) {
        this.eventManager.broadcast({ name: 'messageListModification', content: 'OK'});
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
    selector: 'jhi-message-jo-angular-popup',
    template: ''
})
export class MessageJoAngularPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private messagePopupService: MessageJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.messagePopupService
                    .open(MessageJoAngularDialogComponent as Component, params['id']);
            } else {
                this.messagePopupService
                    .open(MessageJoAngularDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
