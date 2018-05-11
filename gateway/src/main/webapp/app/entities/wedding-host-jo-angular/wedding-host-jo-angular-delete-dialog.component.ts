import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingHostJoAngular } from './wedding-host-jo-angular.model';
import { WeddingHostJoAngularPopupService } from './wedding-host-jo-angular-popup.service';
import { WeddingHostJoAngularService } from './wedding-host-jo-angular.service';

@Component({
    selector: 'jhi-wedding-host-jo-angular-delete-dialog',
    templateUrl: './wedding-host-jo-angular-delete-dialog.component.html'
})
export class WeddingHostJoAngularDeleteDialogComponent {

    weddingHost: WeddingHostJoAngular;

    constructor(
        private weddingHostService: WeddingHostJoAngularService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.weddingHostService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'weddingHostListModification',
                content: 'Deleted an weddingHost'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wedding-host-jo-angular-delete-popup',
    template: ''
})
export class WeddingHostJoAngularDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weddingHostPopupService: WeddingHostJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.weddingHostPopupService
                .open(WeddingHostJoAngularDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
