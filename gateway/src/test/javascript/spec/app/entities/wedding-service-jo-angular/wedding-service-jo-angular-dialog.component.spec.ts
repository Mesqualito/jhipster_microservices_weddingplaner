/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingServiceJoAngularDialogComponent } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular-dialog.component';
import { WeddingServiceJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular.service';
import { WeddingServiceJoAngular } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular.model';
import { AddressJoAngularService } from '../../../../../../main/webapp/app/entities/address-jo-angular';
import { AppUserJoAngularService } from '../../../../../../main/webapp/app/entities/app-user-jo-angular';

describe('Component Tests', () => {

    describe('WeddingServiceJoAngular Management Dialog Component', () => {
        let comp: WeddingServiceJoAngularDialogComponent;
        let fixture: ComponentFixture<WeddingServiceJoAngularDialogComponent>;
        let service: WeddingServiceJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingServiceJoAngularDialogComponent],
                providers: [
                    AddressJoAngularService,
                    AppUserJoAngularService,
                    WeddingServiceJoAngularService
                ]
            })
            .overrideTemplate(WeddingServiceJoAngularDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingServiceJoAngularDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingServiceJoAngularService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WeddingServiceJoAngular(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.weddingService = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'weddingServiceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WeddingServiceJoAngular();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.weddingService = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'weddingServiceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
