import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { WeddingServiceJoAngular } from './wedding-service-jo-angular.model';
import { WeddingServiceJoAngularService } from './wedding-service-jo-angular.service';

@Injectable()
export class WeddingServiceJoAngularPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private weddingServiceService: WeddingServiceJoAngularService

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
                this.weddingServiceService.find(id)
                    .subscribe((weddingServiceResponse: HttpResponse<WeddingServiceJoAngular>) => {
                        const weddingService: WeddingServiceJoAngular = weddingServiceResponse.body;
                        weddingService.serviceCommittedDate = this.datePipe
                            .transform(weddingService.serviceCommittedDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.weddingServiceModalRef(component, weddingService);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.weddingServiceModalRef(component, new WeddingServiceJoAngular());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    weddingServiceModalRef(component: Component, weddingService: WeddingServiceJoAngular): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.weddingService = weddingService;
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
