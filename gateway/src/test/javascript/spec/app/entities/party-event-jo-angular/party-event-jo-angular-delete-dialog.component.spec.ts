/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyEventJoAngularDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/party-event-jo-angular/party-event-jo-angular-delete-dialog.component';
import { PartyEventJoAngularService } from '../../../../../../main/webapp/app/entities/party-event-jo-angular/party-event-jo-angular.service';

describe('Component Tests', () => {

    describe('PartyEventJoAngular Management Delete Component', () => {
        let comp: PartyEventJoAngularDeleteDialogComponent;
        let fixture: ComponentFixture<PartyEventJoAngularDeleteDialogComponent>;
        let service: PartyEventJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyEventJoAngularDeleteDialogComponent],
                providers: [
                    PartyEventJoAngularService
                ]
            })
            .overrideTemplate(PartyEventJoAngularDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyEventJoAngularDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyEventJoAngularService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
