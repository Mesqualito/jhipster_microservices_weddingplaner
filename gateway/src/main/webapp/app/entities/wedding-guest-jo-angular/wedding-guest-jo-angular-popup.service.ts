import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { WeddingGuestJoAngular } from './wedding-guest-jo-angular.model';
import { WeddingGuestJoAngularService } from './wedding-guest-jo-angular.service';

@Injectable()
export class WeddingGuestJoAngularPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private weddingGuestService: WeddingGuestJoAngularService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.weddingGuestService.find(id)
                    .subscribe((weddingGuestResponse: HttpResponse<WeddingGuestJoAngular>) => {
                        const weddingGuest: WeddingGuestJoAngular = weddingGuestResponse.body;
                        weddingGuest.guestInvitationDate = this.datePipe
                            .transform(weddingGuest.guestInvitationDate, 'yyyy-MM-ddTHH:mm:ss');
                        weddingGuest.guestRsvpDate = this.datePipe
                            .transform(weddingGuest.guestRsvpDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.weddingGuestModalRef(component, weddingGuest);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.weddingGuestModalRef(component, new WeddingGuestJoAngular());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    weddingGuestModalRef(component: Component, weddingGuest: WeddingGuestJoAngular): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.weddingGuest = weddingGuest;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
