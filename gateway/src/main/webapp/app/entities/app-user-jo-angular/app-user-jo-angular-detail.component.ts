import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AppUserJoAngular } from './app-user-jo-angular.model';
import { AppUserJoAngularService } from './app-user-jo-angular.service';

@Component({
    selector: 'jhi-app-user-jo-angular-detail',
    templateUrl: './app-user-jo-angular-detail.component.html'
})
export class AppUserJoAngularDetailComponent implements OnInit, OnDestroy {

    appUser: AppUserJoAngular;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private appUserService: AppUserJoAngularService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAppUsers();
    }

    load(id) {
        this.appUserService.find(id)
            .subscribe((appUserResponse: HttpResponse<AppUserJoAngular>) => {
                this.appUser = appUserResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAppUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'appUserListModification',
            (response) => this.load(this.appUser.id)
        );
    }
}
