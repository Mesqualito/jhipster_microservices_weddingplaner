import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MessageJoAngular } from './message-jo-angular.model';
import { MessageJoAngularPopupService } from './message-jo-angular-popup.service';
import { MessageJoAngularService } from './message-jo-angular.service';

@Component({
    selector: 'jhi-message-jo-angular-delete-dialog',
    templateUrl: './message-jo-angular-delete-dialog.component.html'
})
export class MessageJoAngularDeleteDialogComponent {

    message: MessageJoAngular;

    constructor(
        private messageService: MessageJoAngularService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.messageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'messageListModification',
                content: 'Deleted an message'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-message-jo-angular-delete-popup',
    template: ''
})
export class MessageJoAngularDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private messagePopupService: MessageJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.messagePopupService
                .open(MessageJoAngularDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
