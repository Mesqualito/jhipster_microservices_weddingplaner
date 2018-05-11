import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { PartyEventJoAngular } from './party-event-jo-angular.model';
import { PartyEventJoAngularService } from './party-event-jo-angular.service';

@Injectable()
export class PartyEventJoAngularPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private partyEventService: PartyEventJoAngularService

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
                this.partyEventService.find(id)
                    .subscribe((partyEventResponse: HttpResponse<PartyEventJoAngular>) => {
                        const partyEvent: PartyEventJoAngular = partyEventResponse.body;
                        partyEvent.eventInitTime = this.datePipe
                            .transform(partyEvent.eventInitTime, 'yyyy-MM-ddTHH:mm:ss');
                        if (partyEvent.eventStartTime) {
                            partyEvent.eventStartTime = {
                                year: partyEvent.eventStartTime.getFullYear(),
                                month: partyEvent.eventStartTime.getMonth() + 1,
                                day: partyEvent.eventStartTime.getDate()
                            };
                        }
                        if (partyEvent.eventEndTime) {
                            partyEvent.eventEndTime = {
                                year: partyEvent.eventEndTime.getFullYear(),
                                month: partyEvent.eventEndTime.getMonth() + 1,
                                day: partyEvent.eventEndTime.getDate()
                            };
                        }
                        this.ngbModalRef = this.partyEventModalRef(component, partyEvent);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.partyEventModalRef(component, new PartyEventJoAngular());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    partyEventModalRef(component: Component, partyEvent: PartyEventJoAngular): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.partyEvent = partyEvent;
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
