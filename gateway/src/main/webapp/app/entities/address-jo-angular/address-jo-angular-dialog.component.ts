import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AddressJoAngular } from './address-jo-angular.model';
import { AddressJoAngularPopupService } from './address-jo-angular-popup.service';
import { AddressJoAngularService } from './address-jo-angular.service';
import { WeddingGuestJoAngular, WeddingGuestJoAngularService } from '../wedding-guest-jo-angular';
import { WeddingServiceJoAngular, WeddingServiceJoAngularService } from '../wedding-service-jo-angular';

@Component({
    selector: 'jhi-address-jo-angular-dialog',
    templateUrl: './address-jo-angular-dialog.component.html'
})
export class AddressJoAngularDialogComponent implements OnInit {

    address: AddressJoAngular;
    isSaving: boolean;

    weddingguests: WeddingGuestJoAngular[];

    weddingservices: WeddingServiceJoAngular[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private addressService: AddressJoAngularService,
        private weddingGuestService: WeddingGuestJoAngularService,
        private weddingServiceService: WeddingServiceJoAngularService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.weddingGuestService.query()
            .subscribe((res: HttpResponse<WeddingGuestJoAngular[]>) => { this.weddingguests = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.weddingServiceService.query()
            .subscribe((res: HttpResponse<WeddingServiceJoAngular[]>) => { this.weddingservices = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.address.id !== undefined) {
            this.subscribeToSaveResponse(
                this.addressService.update(this.address));
        } else {
            this.subscribeToSaveResponse(
                this.addressService.create(this.address));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AddressJoAngular>>) {
        result.subscribe((res: HttpResponse<AddressJoAngular>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AddressJoAngular) {
        this.eventManager.broadcast({ name: 'addressListModification', content: 'OK'});
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

    trackWeddingServiceById(index: number, item: WeddingServiceJoAngular) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-address-jo-angular-popup',
    template: ''
})
export class AddressJoAngularPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressPopupService: AddressJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.addressPopupService
                    .open(AddressJoAngularDialogComponent as Component, params['id']);
            } else {
                this.addressPopupService
                    .open(AddressJoAngularDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
