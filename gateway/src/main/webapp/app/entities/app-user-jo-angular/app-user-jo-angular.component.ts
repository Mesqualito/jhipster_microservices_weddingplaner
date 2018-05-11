import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AppUserJoAngular } from './app-user-jo-angular.model';
import { AppUserJoAngularService } from './app-user-jo-angular.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-app-user-jo-angular',
    templateUrl: './app-user-jo-angular.component.html'
})
export class AppUserJoAngularComponent implements OnInit, OnDestroy {
appUsers: AppUserJoAngular[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private appUserService: AppUserJoAngularService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.appUserService.query().subscribe(
            (res: HttpResponse<AppUserJoAngular[]>) => {
                this.appUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAppUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AppUserJoAngular) {
        return item.id;
    }
    registerChangeInAppUsers() {
        this.eventSubscriber = this.eventManager.subscribe('appUserListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
