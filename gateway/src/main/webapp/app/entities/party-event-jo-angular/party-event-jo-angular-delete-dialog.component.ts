import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PartyEventJoAngular } from './party-event-jo-angular.model';
import { PartyEventJoAngularPopupService } from './party-event-jo-angular-popup.service';
import { PartyEventJoAngularService } from './party-event-jo-angular.service';

@Component({
    selector: 'jhi-party-event-jo-angular-delete-dialog',
    templateUrl: './party-event-jo-angular-delete-dialog.component.html'
})
export class PartyEventJoAngularDeleteDialogComponent {

    partyEvent: PartyEventJoAngular;

    constructor(
        private partyEventService: PartyEventJoAngularService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partyEventService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'partyEventListModification',
                content: 'Deleted an partyEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-party-event-jo-angular-delete-popup',
    template: ''
})
export class PartyEventJoAngularDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partyEventPopupService: PartyEventJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.partyEventPopupService
                .open(PartyEventJoAngularDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
