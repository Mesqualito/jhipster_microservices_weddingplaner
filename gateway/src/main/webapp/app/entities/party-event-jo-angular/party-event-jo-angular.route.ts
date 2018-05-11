import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PartyEventJoAngularComponent } from './party-event-jo-angular.component';
import { PartyEventJoAngularDetailComponent } from './party-event-jo-angular-detail.component';
import { PartyEventJoAngularPopupComponent } from './party-event-jo-angular-dialog.component';
import { PartyEventJoAngularDeletePopupComponent } from './party-event-jo-angular-delete-dialog.component';

@Injectable()
export class PartyEventJoAngularResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const partyEventRoute: Routes = [
    {
        path: 'party-event-jo-angular',
        component: PartyEventJoAngularComponent,
        resolve: {
            'pagingParams': PartyEventJoAngularResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'party-event-jo-angular/:id',
        component: PartyEventJoAngularDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partyEventPopupRoute: Routes = [
    {
        path: 'party-event-jo-angular-new',
        component: PartyEventJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyEvent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'party-event-jo-angular/:id/edit',
        component: PartyEventJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyEvent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'party-event-jo-angular/:id/delete',
        component: PartyEventJoAngularDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyEvent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
