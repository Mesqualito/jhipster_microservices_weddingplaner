/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyFoodJoAngularDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular-delete-dialog.component';
import { PartyFoodJoAngularService } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular.service';

describe('Component Tests', () => {

    describe('PartyFoodJoAngular Management Delete Component', () => {
        let comp: PartyFoodJoAngularDeleteDialogComponent;
        let fixture: ComponentFixture<PartyFoodJoAngularDeleteDialogComponent>;
        let service: PartyFoodJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyFoodJoAngularDeleteDialogComponent],
                providers: [
                    PartyFoodJoAngularService
                ]
            })
            .overrideTemplate(PartyFoodJoAngularDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyFoodJoAngularDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyFoodJoAngularService);
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
