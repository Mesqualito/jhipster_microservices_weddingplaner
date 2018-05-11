import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PartyFoodJoAngularComponent } from './party-food-jo-angular.component';
import { PartyFoodJoAngularDetailComponent } from './party-food-jo-angular-detail.component';
import { PartyFoodJoAngularPopupComponent } from './party-food-jo-angular-dialog.component';
import { PartyFoodJoAngularDeletePopupComponent } from './party-food-jo-angular-delete-dialog.component';

@Injectable()
export class PartyFoodJoAngularResolvePagingParams implements Resolve<any> {

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

export const partyFoodRoute: Routes = [
    {
        path: 'party-food-jo-angular',
        component: PartyFoodJoAngularComponent,
        resolve: {
            'pagingParams': PartyFoodJoAngularResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'party-food-jo-angular/:id',
        component: PartyFoodJoAngularDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partyFoodPopupRoute: Routes = [
    {
        path: 'party-food-jo-angular-new',
        component: PartyFoodJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'party-food-jo-angular/:id/edit',
        component: PartyFoodJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'party-food-jo-angular/:id/delete',
        component: PartyFoodJoAngularDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
