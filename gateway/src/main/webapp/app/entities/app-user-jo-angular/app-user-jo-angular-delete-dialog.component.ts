import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AppUserJoAngular } from './app-user-jo-angular.model';
import { AppUserJoAngularPopupService } from './app-user-jo-angular-popup.service';
import { AppUserJoAngularService } from './app-user-jo-angular.service';

@Component({
    selector: 'jhi-app-user-jo-angular-delete-dialog',
    templateUrl: './app-user-jo-angular-delete-dialog.component.html'
})
export class AppUserJoAngularDeleteDialogComponent {

    appUser: AppUserJoAngular;

    constructor(
        private appUserService: AppUserJoAngularService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'appUserListModification',
                content: 'Deleted an appUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-app-user-jo-angular-delete-popup',
    template: ''
})
export class AppUserJoAngularDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appUserPopupService: AppUserJoAngularPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.appUserPopupService
                .open(AppUserJoAngularDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
