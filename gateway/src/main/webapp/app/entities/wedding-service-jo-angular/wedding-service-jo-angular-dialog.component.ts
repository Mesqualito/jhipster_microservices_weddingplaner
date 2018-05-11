import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WeddingServiceJoAngular } from './wedding-service-jo-angular.model';
import { WeddingServiceJoAngularPopupService } from './wedding-service-jo-angular-popup.service';
import { WeddingServiceJoAngularService } from './wedding-service-jo-angular.service';
import { AddressJoAngular, AddressJoAngularService } from '../address-jo-angular';
import { AppUserJoAngular, AppUserJoAngularService } from '../app-user-jo-angular';

@Component({
    selector: 'jhi-wedding-service-jo-angular-dialog',
    templateUrl: './wedding-service-jo-angular-dialog.component.html'
})
export class WeddingServiceJoAngularDialogComponent implements OnInit {

    weddingService: WeddingServiceJoAngular;
    isSaving: boolean;

    businessaddresses: AddressJoAngular[];

    privateaddresses: AddressJoAngular[];

    appusers: AppUserJoAngular[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private weddingServiceService: WeddingServiceJoAngularService,
        private addressService: AddressJoAngularService,
        private appUserService: AppUserJoAngularService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.addressService
            .query({filter: 'servicebusiness-is-null'})
            .subscribe((res: HttpResponse<AddressJoAngular[]>) => {
                if (!this.weddingService.businessAddressId) {
                    this.businessaddresses = res.body;
                } else {
                    this.addressService
                        .find(this.weddingService.businessAddressId)
                        .subscribe((subRes: HttpResponse<AddressJoAngular>) => {
                            this.businessaddresses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.addressService
            .query({filter: 'serviceprivate-is-null'})
            .subscribe((res: HttpResponse<AddressJoAngular[]>) => {
                if (!this.weddingService.privateAddressId) {
                    this.privateaddresses = res.body;
                } else {
                    this.addressService
                        .find(this.weddingService.privateAddressId)
                        .subscribe((subRes: HttpResponse<AddressJoAngular>) => {
                            this.privateaddresses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.appUserService
            .query({filter: 'weddingservice-is-null'})
            .subscribe((res: HttpResponse<AppUserJoAngular[]>) => {
                if (!this.weddingService.appUserId) {
                    this.appusers = res.body;
                } else {
                    this.appUserService
                        .find(this.weddingService.appUserId)
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
        if (this.weddingService.id !== undefined) {
            this.subscribeToSaveResponse(
                this.weddingServiceService.update(this.weddingService));
        } else {
            this.subscribeToSaveResponse(
                this.weddingServiceService.create(this.weddingService));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<WeddingServiceJoAngular>>) {
        result.subscribe((res: HttpResponse<WeddingServiceJoAngular>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: WeddingServiceJoAngular) {
        this.eventManager.broadcast({ name: 'weddingServiceListModification', content: 'OK'});
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
    selector: 'jhi-wedding-service-jo-angular-popup',
    template: ''
})
export class WeddingServiceJoAngularPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weddingServicePopupService: WeddingServiceJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.weddingServicePopupService
                    .open(WeddingServiceJoAngularDialogComponent as Component, params['id']);
            } else {
                this.weddingServicePopupService
                    .open(WeddingServiceJoAngularDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
