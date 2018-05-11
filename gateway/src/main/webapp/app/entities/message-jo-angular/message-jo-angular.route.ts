import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MessageJoAngularComponent } from './message-jo-angular.component';
import { MessageJoAngularDetailComponent } from './message-jo-angular-detail.component';
import { MessageJoAngularPopupComponent } from './message-jo-angular-dialog.component';
import { MessageJoAngularDeletePopupComponent } from './message-jo-angular-delete-dialog.component';

@Injectable()
export class MessageJoAngularResolvePagingParams implements Resolve<any> {

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

export const messageRoute: Routes = [
    {
        path: 'message-jo-angular',
        component: MessageJoAngularComponent,
        resolve: {
            'pagingParams': MessageJoAngularResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.message.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'message-jo-angular/:id',
        component: MessageJoAngularDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.message.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const messagePopupRoute: Routes = [
    {
        path: 'message-jo-angular-new',
        component: MessageJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.message.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message-jo-angular/:id/edit',
        component: MessageJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.message.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message-jo-angular/:id/delete',
        component: MessageJoAngularDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.message.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
