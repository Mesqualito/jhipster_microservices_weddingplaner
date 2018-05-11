/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingGuestJoAngularDialogComponent } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular-dialog.component';
import { WeddingGuestJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular.service';
import { WeddingGuestJoAngular } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular.model';
import { AddressJoAngularService } from '../../../../../../main/webapp/app/entities/address-jo-angular';
import { AppUserJoAngularService } from '../../../../../../main/webapp/app/entities/app-user-jo-angular';

describe('Component Tests', () => {

    describe('WeddingGuestJoAngular Management Dialog Component', () => {
        let comp: WeddingGuestJoAngularDialogComponent;
        let fixture: ComponentFixture<WeddingGuestJoAngularDialogComponent>;
        let service: WeddingGuestJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingGuestJoAngularDialogComponent],
                providers: [
                    AddressJoAngularService,
                    AppUserJoAngularService,
                    WeddingGuestJoAngularService
                ]
            })
            .overrideTemplate(WeddingGuestJoAngularDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingGuestJoAngularDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingGuestJoAngularService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WeddingGuestJoAngular(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.weddingGuest = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'weddingGuestListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WeddingGuestJoAngular();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.weddingGuest = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'weddingGuestListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
