/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingplanerTestModule } from '../../../test.module';
import { AddressJoAngularDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular-delete-dialog.component';
import { AddressJoAngularService } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular.service';

describe('Component Tests', () => {

    describe('AddressJoAngular Management Delete Component', () => {
        let comp: AddressJoAngularDeleteDialogComponent;
        let fixture: ComponentFixture<AddressJoAngularDeleteDialogComponent>;
        let service: AddressJoAngularService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AddressJoAngularDeleteDialogComponent],
                providers: [
                    AddressJoAngularService
                ]
            })
            .overrideTemplate(AddressJoAngularDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressJoAngularDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressJoAngularService);
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
