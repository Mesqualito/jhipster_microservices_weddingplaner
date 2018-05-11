import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PartyFoodJoAngular } from './party-food-jo-angular.model';
import { PartyFoodJoAngularPopupService } from './party-food-jo-angular-popup.service';
import { PartyFoodJoAngularService } from './party-food-jo-angular.service';

@Component({
    selector: 'jhi-party-food-jo-angular-delete-dialog',
    templateUrl: './party-food-jo-angular-delete-dialog.component.html'
})
export class PartyFoodJoAngularDeleteDialogComponent {

    partyFood: PartyFoodJoAngular;

    constructor(
        private partyFoodService: PartyFoodJoAngularService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partyFoodService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'partyFoodListModification',
                content: 'Deleted an partyFood'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-party-food-jo-angular-delete-popup',
    template: ''
})
export class PartyFoodJoAngularDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partyFoodPopupService: PartyFoodJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.partyFoodPopupService
                .open(PartyFoodJoAngularDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
