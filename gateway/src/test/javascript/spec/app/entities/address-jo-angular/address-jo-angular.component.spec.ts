/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { AddressJoAngularComponent } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular.component';
import { AddressJoAngularService } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular.service';
import { AddressJoAngular } from '../../../../../../main/webapp/app/entities/address-jo-angular/address-jo-angular.model';

describe('Component Tests', () => {

    describe('AddressJoAngular Management Component', () => {
        let comp: AddressJoAngularComponent;
        let fixture: ComponentFixture<AddressJoAngularComponent>;
        let service: AddressJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AddressJoAngularComponent],
                providers: [
                    AddressJoAngularService
                ]
            })
            .overrideTemplate(AddressJoAngularComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressJoAngularComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AddressJoAngular(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.addresses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
