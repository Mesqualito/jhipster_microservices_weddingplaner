/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingHostJoAngularDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular-delete-dialog.component';
import { WeddingHostJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular.service';

describe('Component Tests', () => {

    describe('WeddingHostJoAngular Management Delete Component', () => {
        let comp: WeddingHostJoAngularDeleteDialogComponent;
        let fixture: ComponentFixture<WeddingHostJoAngularDeleteDialogComponent>;
        let service: WeddingHostJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingHostJoAngularDeleteDialogComponent],
                providers: [
                    WeddingHostJoAngularService
                ]
            })
            .overrideTemplate(WeddingHostJoAngularDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingHostJoAngularDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingHostJoAngularService);
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
