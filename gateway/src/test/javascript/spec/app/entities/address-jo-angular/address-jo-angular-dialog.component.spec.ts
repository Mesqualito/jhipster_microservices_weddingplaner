/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { AddressJoAngularDialogComponent } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular-dialog.component';
import { AddressJoAngularService } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular.service';
import { AddressJoAngular } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular.model';
import { WeddingGuestJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular';
import { WeddingServiceJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular';

describe('Component Tests', () => {

    describe('AddressJoAngular Management Dialog Component', () => {
        let comp: AddressJoAngularDialogComponent;
        let fixture: ComponentFixture<AddressJoAngularDialogComponent>;
        let service: AddressJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AddressJoAngularDialogComponent],
                providers: [
                    WeddingGuestJoAngularService,
                    WeddingServiceJoAngularService,
                    AddressJoAngularService
                ]
            })
            .overrideTemplate(AddressJoAngularDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressJoAngularDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressJoAngularService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AddressJoAngular(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.address = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'addressListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AddressJoAngular();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.address = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'addressListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
