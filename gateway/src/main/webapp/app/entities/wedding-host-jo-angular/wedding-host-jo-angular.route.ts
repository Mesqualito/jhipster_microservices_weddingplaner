import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { WeddingHostJoAngularComponent } from './wedding-host-jo-angular.component';
import { WeddingHostJoAngularDetailComponent } from './wedding-host-jo-angular-detail.component';
import { WeddingHostJoAngularPopupComponent } from './wedding-host-jo-angular-dialog.component';
import { WeddingHostJoAngularDeletePopupComponent } from './wedding-host-jo-angular-delete-dialog.component';

export const weddingHostRoute: Routes = [
    {
        path: 'wedding-host-jo-angular',
        component: WeddingHostJoAngularComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingHost.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wedding-host-jo-angular/:id',
        component: WeddingHostJoAngularDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingHost.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const weddingHostPopupRoute: Routes = [
    {
        path: 'wedding-host-jo-angular-new',
        component: WeddingHostJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingHost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wedding-host-jo-angular/:id/edit',
        component: WeddingHostJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingHost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wedding-host-jo-angular/:id/delete',
        component: WeddingHostJoAngularDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingHost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
