/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { AppUserJoAngularDialogComponent } from '../../../../../../main/webapp/app/entities/app-user-jo-angular/app-user-jo-angular-dialog.component';
import { AppUserJoAngularService } from '../../../../../../main/webapp/app/entities/app-user-jo-angular/app-user-jo-angular.service';
import { AppUserJoAngular } from '../../../../../../main/webapp/app/entities/app-user-jo-angular/app-user-jo-angular.model';
import { WeddingGuestJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular';
import { WeddingHostJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular';
import { WeddingServiceJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular';
import { PartyFoodJoAngularService } from '../../../../../../main/webapp/app/entities/party-food-jo-angular';
import { MessageJoAngularService } from '../../../../../../main/webapp/app/entities/message-jo-angular';

describe('Component Tests', () => {

    describe('AppUserJoAngular Management Dialog Component', () => {
        let comp: AppUserJoAngularDialogComponent;
        let fixture: ComponentFixture<AppUserJoAngularDialogComponent>;
        let service: AppUserJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AppUserJoAngularDialogComponent],
                providers: [
                    WeddingGuestJoAngularService,
                    WeddingHostJoAngularService,
                    WeddingServiceJoAngularService,
                    PartyFoodJoAngularService,
                    MessageJoAngularService,
                    AppUserJoAngularService
                ]
            })
            .overrideTemplate(AppUserJoAngularDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppUserJoAngularDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppUserJoAngularService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AppUserJoAngular(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.appUser = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'appUserListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AppUserJoAngular();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.appUser = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'appUserListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
