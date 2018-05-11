/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { MessageJoAngularDialogComponent } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular-dialog.component';
import { MessageJoAngularService } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular.service';
import { MessageJoAngular } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular.model';
import { AppUserJoAngularService } from '../../../../../../main/webapp/app/entities/app-user-jo-angular';

describe('Component Tests', () => {

    describe('MessageJoAngular Management Dialog Component', () => {
        let comp: MessageJoAngularDialogComponent;
        let fixture: ComponentFixture<MessageJoAngularDialogComponent>;
        let service: MessageJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [MessageJoAngularDialogComponent],
                providers: [
                    AppUserJoAngularService,
                    MessageJoAngularService
                ]
            })
            .overrideTemplate(MessageJoAngularDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MessageJoAngularDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MessageJoAngularService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MessageJoAngular(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.message = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'messageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MessageJoAngular();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.message = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'messageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
