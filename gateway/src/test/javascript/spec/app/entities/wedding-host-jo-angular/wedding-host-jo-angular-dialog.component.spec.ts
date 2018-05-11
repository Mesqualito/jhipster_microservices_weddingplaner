/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingHostJoAngularDialogComponent } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular-dialog.component';
import { WeddingHostJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular.service';
import { WeddingHostJoAngular } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular.model';
import { AppUserJoAngularService } from '../../../../../../main/webapp/app/entities/app-user-jo-angular';
import { PartyFoodJoAngularService } from '../../../../../../main/webapp/app/entities/party-food-jo-angular';

describe('Component Tests', () => {

    describe('WeddingHostJoAngular Management Dialog Component', () => {
        let comp: WeddingHostJoAngularDialogComponent;
        let fixture: ComponentFixture<WeddingHostJoAngularDialogComponent>;
        let service: WeddingHostJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingHostJoAngularDialogComponent],
                providers: [
                    AppUserJoAngularService,
                    PartyFoodJoAngularService,
                    WeddingHostJoAngularService
                ]
            })
            .overrideTemplate(WeddingHostJoAngularDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingHostJoAngularDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingHostJoAngularService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WeddingHostJoAngular(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.weddingHost = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'weddingHostListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new WeddingHostJoAngular();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.weddingHost = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'weddingHostListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
