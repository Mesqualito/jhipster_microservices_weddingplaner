/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyFoodJoAngularDialogComponent } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular-dialog.component';
import { PartyFoodJoAngularService } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular.service';
import { PartyFoodJoAngular } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular.model';
import { AppUserJoAngularService } from '../../../../../../main/webapp/app/entities/app-user-jo-angular';
import { WeddingHostJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular';

describe('Component Tests', () => {

    describe('PartyFoodJoAngular Management Dialog Component', () => {
        let comp: PartyFoodJoAngularDialogComponent;
        let fixture: ComponentFixture<PartyFoodJoAngularDialogComponent>;
        let service: PartyFoodJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyFoodJoAngularDialogComponent],
                providers: [
                    AppUserJoAngularService,
                    WeddingHostJoAngularService,
                    PartyFoodJoAngularService
                ]
            })
            .overrideTemplate(PartyFoodJoAngularDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyFoodJoAngularDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyFoodJoAngularService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PartyFoodJoAngular(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.partyFood = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'partyFoodListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PartyFoodJoAngular();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.partyFood = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'partyFoodListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
