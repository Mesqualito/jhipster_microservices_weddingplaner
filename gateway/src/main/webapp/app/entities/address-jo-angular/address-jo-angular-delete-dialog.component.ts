import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AddressJoAngular } from './address-jo-angular.model';
import { AddressJoAngularPopupService } from './address-jo-angular-popup.service';
import { AddressJoAngularService } from './address-jo-angular.service';

@Component({
    selector: 'jhi-address-jo-angular-delete-dialog',
    templateUrl: './address-jo-angular-delete-dialog.component.html'
})
export class AddressJoAngularDeleteDialogComponent {

    address: AddressJoAngular;

    constructor(
        private addressService: AddressJoAngularService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.addressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'addressListModification',
                content: 'Deleted an address'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-address-jo-angular-delete-popup',
    template: ''
})
export class AddressJoAngularDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressPopupService: AddressJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.addressPopupService
                .open(AddressJoAngularDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
