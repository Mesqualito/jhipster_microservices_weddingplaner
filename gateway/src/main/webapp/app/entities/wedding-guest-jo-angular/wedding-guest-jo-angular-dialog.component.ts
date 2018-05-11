import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WeddingGuestJoAngular } from './wedding-guest-jo-angular.model';
import { WeddingGuestJoAngularPopupService } from './wedding-guest-jo-angular-popup.service';
import { WeddingGuestJoAngularService } from './wedding-guest-jo-angular.service';
import { AddressJoAngular, AddressJoAngularService } from '../address-jo-angular';
import { AppUserJoAngular, AppUserJoAngularService } from '../app-user-jo-angular';

@Component({
    selector: 'jhi-wedding-guest-jo-angular-dialog',
    templateUrl: './wedding-guest-jo-angular-dialog.component.html'
})
export class WeddingGuestJoAngularDialogComponent implements OnInit {

    weddingGuest: WeddingGuestJoAngular;
    isSaving: boolean;

    privateaddresses: AddressJoAngular[];

    businessaddresses: AddressJoAngular[];

    appusers: AppUserJoAngular[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private weddingGuestService: WeddingGuestJoAngularService,
        private addressService: AddressJoAngularService,
        private appUserService: AppUserJoAngularService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.addressService
            .query({filter: 'guestprivate-is-null'})
            .subscribe((res: HttpResponse<AddressJoAngular[]>) => {
                if (!this.weddingGuest.privateAddressId) {
                    this.privateaddresses = res.body;
                } else {
                    this.addressService
                        .find(this.weddingGuest.privateAddressId)
                        .subscribe((subRes: HttpResponse<AddressJoAngular>) => {
                            this.privateaddresses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.addressService
            .query({filter: 'guestbusiness-is-null'})
            .subscribe((res: HttpResponse<AddressJoAngular[]>) => {
                if (!this.weddingGuest.businessAddressId) {
                    this.businessaddresses = res.body;
                } else {
                    this.addressService
                        .find(this.weddingGuest.businessAddressId)
                        .subscribe((subRes: HttpResponse<AddressJoAngular>) => {
                            this.businessaddresses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.appUserService
            .query({filter: 'weddingguest-is-null'})
            .subscribe((res: HttpResponse<AppUserJoAngular[]>) => {
                if (!this.weddingGuest.appUserId) {
                    this.appusers = res.body;
                } else {
                    this.appUserService
                        .find(this.weddingGuest.appUserId)
                        .subscribe((subRes: HttpResponse<AppUserJoAngular>) => {
                            this.appusers = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.weddingGuest.id !== undefined) {
            this.subscribeToSaveResponse(
                this.weddingGuestService.update(this.weddingGuest));
        } else {
            this.subscribeToSaveResponse(
                this.weddingGuestService.create(this.weddingGuest));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<WeddingGuestJoAngular>>) {
        result.subscribe((res: HttpResponse<WeddingGuestJoAngular>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: WeddingGuestJoAngular) {
        this.eventManager.broadcast({ name: 'weddingGuestListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAddressById(index: number, item: AddressJoAngular) {
        return item.id;
    }

    trackAppUserById(index: number, item: AppUserJoAngular) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-wedding-guest-jo-angular-popup',
    template: ''
})
export class WeddingGuestJoAngularPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weddingGuestPopupService: WeddingGuestJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.weddingGuestPopupService
                    .open(WeddingGuestJoAngularDialogComponent as Component, params['id']);
            } else {
                this.weddingGuestPopupService
                    .open(WeddingGuestJoAngularDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
