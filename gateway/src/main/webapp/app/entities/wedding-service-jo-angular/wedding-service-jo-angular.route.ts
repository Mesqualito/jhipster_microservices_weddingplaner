import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { WeddingServiceJoAngularComponent } from './wedding-service-jo-angular.component';
import { WeddingServiceJoAngularDetailComponent } from './wedding-service-jo-angular-detail.component';
import { WeddingServiceJoAngularPopupComponent } from './wedding-service-jo-angular-dialog.component';
import { WeddingServiceJoAngularDeletePopupComponent } from './wedding-service-jo-angular-delete-dialog.component';

export const weddingServiceRoute: Routes = [
    {
        path: 'wedding-service-jo-angular',
        component: WeddingServiceJoAngularComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wedding-service-jo-angular/:id',
        component: WeddingServiceJoAngularDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const weddingServicePopupRoute: Routes = [
    {
        path: 'wedding-service-jo-angular-new',
        component: WeddingServiceJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wedding-service-jo-angular/:id/edit',
        component: WeddingServiceJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wedding-service-jo-angular/:id/delete',
        component: WeddingServiceJoAngularDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.weddingService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
