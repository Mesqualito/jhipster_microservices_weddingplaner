/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { AddressJoAngularDetailComponent } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular-detail.component';
import { AddressJoAngularService } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular.service';
import { AddressJoAngular } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular.model';

describe('Component Tests', () => {

    describe('AddressJoAngular Management Detail Component', () => {
        let comp: AddressJoAngularDetailComponent;
        let fixture: ComponentFixture<AddressJoAngularDetailComponent>;
        let service: AddressJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AddressJoAngularDetailComponent],
                providers: [
                    AddressJoAngularService
                ]
            })
            .overrideTemplate(AddressJoAngularDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressJoAngularDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AddressJoAngular(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.address).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
