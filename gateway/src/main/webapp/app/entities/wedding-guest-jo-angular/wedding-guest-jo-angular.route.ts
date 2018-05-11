import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { WeddingGuestJoAngularComponent } from './wedding-guest-jo-angular.component';
import { WeddingGuestJoAngularDetailComponent } from './wedding-guest-jo-angular-detail.component';
import { WeddingGuestJoAngularPopupComponent } from './wedding-guest-jo-angular-dialog.component';
import { WeddingGuestJoAngularDeletePopupComponent } from './wedding-guest-jo-angular-delete-dialog.component';

export const weddingGuestRoute: Routes = [
    {
        path: 'wedding-guest-jo-angular',
        component: WeddingGuestJoAngularComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingGuest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wedding-guest-jo-angular/:id',
        component: WeddingGuestJoAngularDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingGuest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const weddingGuestPopupRoute: Routes = [
    {
        path: 'wedding-guest-jo-angular-new',
        component: WeddingGuestJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingGuest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wedding-guest-jo-angular/:id/edit',
        component: WeddingGuestJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingGuest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wedding-guest-jo-angular/:id/delete',
        component: WeddingGuestJoAngularDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingGuest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
