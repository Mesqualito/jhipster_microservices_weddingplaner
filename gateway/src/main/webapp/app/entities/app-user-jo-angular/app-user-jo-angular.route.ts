import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AppUserJoAngularComponent } from './app-user-jo-angular.component';
import { AppUserJoAngularDetailComponent } from './app-user-jo-angular-detail.component';
import { AppUserJoAngularPopupComponent } from './app-user-jo-angular-dialog.component';
import { AppUserJoAngularDeletePopupComponent } from './app-user-jo-angular-delete-dialog.component';

export const appUserRoute: Routes = [
    {
        path: 'app-user-jo-angular',
        component: AppUserJoAngularComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'app-user-jo-angular/:id',
        component: AppUserJoAngularDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appUserPopupRoute: Routes = [
    {
        path: 'app-user-jo-angular-new',
        component: AppUserJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'app-user-jo-angular/:id/edit',
        component: AppUserJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'app-user-jo-angular/:id/delete',
        component: AppUserJoAngularDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
