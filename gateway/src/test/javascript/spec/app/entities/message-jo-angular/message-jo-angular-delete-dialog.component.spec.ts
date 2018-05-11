/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { MessageJoAngularDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular-delete-dialog.component';
import { MessageJoAngularService } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular.service';

describe('Component Tests', () => {

    describe('MessageJoAngular Management Delete Component', () => {
        let comp: MessageJoAngularDeleteDialogComponent;
        let fixture: ComponentFixture<MessageJoAngularDeleteDialogComponent>;
        let service: MessageJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [MessageJoAngularDeleteDialogComponent],
                providers: [
                    MessageJoAngularService
                ]
            })
            .overrideTemplate(MessageJoAngularDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MessageJoAngularDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MessageJoAngularService);
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
