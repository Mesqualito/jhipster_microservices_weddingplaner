import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { PartyFoodJoAngular } from './party-food-jo-angular.model';
import { PartyFoodJoAngularService } from './party-food-jo-angular.service';

@Injectable()
export class PartyFoodJoAngularPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private partyFoodService: PartyFoodJoAngularService

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
                this.partyFoodService.find(id)
                    .subscribe((partyFoodResponse: HttpResponse<PartyFoodJoAngular>) => {
                        const partyFood: PartyFoodJoAngular = partyFoodResponse.body;
                        if (partyFood.foodBestServeTime) {
                            partyFood.foodBestServeTime = {
                                year: partyFood.foodBestServeTime.getFullYear(),
                                month: partyFood.foodBestServeTime.getMonth() + 1,
                                day: partyFood.foodBestServeTime.getDate()
                            };
                        }
                        partyFood.foodProposalAcceptTime = this.datePipe
                            .transform(partyFood.foodProposalAcceptTime, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.partyFoodModalRef(component, partyFood);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.partyFoodModalRef(component, new PartyFoodJoAngular());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    partyFoodModalRef(component: Component, partyFood: PartyFoodJoAngular): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.partyFood = partyFood;
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
