import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingGuestJoAngular } from './wedding-guest-jo-angular.model';
import { WeddingGuestJoAngularPopupService } from './wedding-guest-jo-angular-popup.service';
import { WeddingGuestJoAngularService } from './wedding-guest-jo-angular.service';

@Component({
    selector: 'jhi-wedding-guest-jo-angular-delete-dialog',
    templateUrl: './wedding-guest-jo-angular-delete-dialog.component.html'
})
export class WeddingGuestJoAngularDeleteDialogComponent {

    weddingGuest: WeddingGuestJoAngular;

    constructor(
        private weddingGuestService: WeddingGuestJoAngularService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.weddingGuestService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'weddingGuestListModification',
                content: 'Deleted an weddingGuest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wedding-guest-jo-angular-delete-popup',
    template: ''
})
export class WeddingGuestJoAngularDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weddingGuestPopupService: WeddingGuestJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.weddingGuestPopupService
                .open(WeddingGuestJoAngularDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
