import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AddressJoAngularComponent } from './address-jo-angular.component';
import { AddressJoAngularDetailComponent } from './address-jo-angular-detail.component';
import { AddressJoAngularPopupComponent } from './address-jo-angular-dialog.component';
import { AddressJoAngularDeletePopupComponent } from './address-jo-angular-delete-dialog.component';

export const addressRoute: Routes = [
    {
        path: 'address-jo-angular',
        component: AddressJoAngularComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.address.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'address-jo-angular/:id',
        component: AddressJoAngularDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.address.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const addressPopupRoute: Routes = [
    {
        path: 'address-jo-angular-new',
        component: AddressJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-jo-angular/:id/edit',
        component: AddressJoAngularPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-jo-angular/:id/delete',
        component: AddressJoAngularDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
