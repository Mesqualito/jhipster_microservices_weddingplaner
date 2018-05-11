/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingServiceJoAngularDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular-delete-dialog.component';
import { WeddingServiceJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular.service';

describe('Component Tests', () => {

    describe('WeddingServiceJoAngular Management Delete Component', () => {
        let comp: WeddingServiceJoAngularDeleteDialogComponent;
        let fixture: ComponentFixture<WeddingServiceJoAngularDeleteDialogComponent>;
        let service: WeddingServiceJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingServiceJoAngularDeleteDialogComponent],
                providers: [
                    WeddingServiceJoAngularService
                ]
            })
            .overrideTemplate(WeddingServiceJoAngularDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingServiceJoAngularDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingServiceJoAngularService);
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
