import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { MessageJoAngular } from './message-jo-angular.model';
import { MessageJoAngularService } from './message-jo-angular.service';

@Injectable()
export class MessageJoAngularPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private messageService: MessageJoAngularService

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
                this.messageService.find(id)
                    .subscribe((messageResponse: HttpResponse<MessageJoAngular>) => {
                        const message: MessageJoAngular = messageResponse.body;
                        message.messageInitTime = this.datePipe
                            .transform(message.messageInitTime, 'yyyy-MM-ddTHH:mm:ss');
                        if (message.messageValidFrom) {
                            message.messageValidFrom = {
                                year: message.messageValidFrom.getFullYear(),
                                month: message.messageValidFrom.getMonth() + 1,
                                day: message.messageValidFrom.getDate()
                            };
                        }
                        if (message.messageValidUntil) {
                            message.messageValidUntil = {
                                year: message.messageValidUntil.getFullYear(),
                                month: message.messageValidUntil.getMonth() + 1,
                                day: message.messageValidUntil.getDate()
                            };
                        }
                        this.ngbModalRef = this.messageModalRef(component, message);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.messageModalRef(component, new MessageJoAngular());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    messageModalRef(component: Component, message: MessageJoAngular): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.message = message;
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
