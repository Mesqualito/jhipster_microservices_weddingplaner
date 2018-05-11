import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { WeddingHostJoAngular } from './wedding-host-jo-angular.model';
import { WeddingHostJoAngularService } from './wedding-host-jo-angular.service';

@Injectable()
export class WeddingHostJoAngularPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private weddingHostService: WeddingHostJoAngularService

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
                this.weddingHostService.find(id)
                    .subscribe((weddingHostResponse: HttpResponse<WeddingHostJoAngular>) => {
                        const weddingHost: WeddingHostJoAngular = weddingHostResponse.body;
                        this.ngbModalRef = this.weddingHostModalRef(component, weddingHost);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.weddingHostModalRef(component, new WeddingHostJoAngular());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    weddingHostModalRef(component: Component, weddingHost: WeddingHostJoAngular): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.weddingHost = weddingHost;
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
