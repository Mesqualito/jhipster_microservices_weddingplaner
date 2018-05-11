import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingServiceJoAngular } from './wedding-service-jo-angular.model';
import { WeddingServiceJoAngularPopupService } from './wedding-service-jo-angular-popup.service';
import { WeddingServiceJoAngularService } from './wedding-service-jo-angular.service';

@Component({
    selector: 'jhi-wedding-service-jo-angular-delete-dialog',
    templateUrl: './wedding-service-jo-angular-delete-dialog.component.html'
})
export class WeddingServiceJoAngularDeleteDialogComponent {

    weddingService: WeddingServiceJoAngular;

    constructor(
        private weddingServiceService: WeddingServiceJoAngularService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.weddingServiceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'weddingServiceListModification',
                content: 'Deleted an weddingService'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wedding-service-jo-angular-delete-popup',
    template: ''
})
export class WeddingServiceJoAngularDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weddingServicePopupService: WeddingServiceJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.weddingServicePopupService
                .open(WeddingServiceJoAngularDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
