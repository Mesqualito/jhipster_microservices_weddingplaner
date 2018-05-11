/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingServiceJoAngularComponent } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular.component';
import { WeddingServiceJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular.service';
import { WeddingServiceJoAngular } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular.model';

describe('Component Tests', () => {

    describe('WeddingServiceJoAngular Management Component', () => {
        let comp: WeddingServiceJoAngularComponent;
        let fixture: ComponentFixture<WeddingServiceJoAngularComponent>;
        let service: WeddingServiceJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingServiceJoAngularComponent],
                providers: [
                    WeddingServiceJoAngularService
                ]
            })
            .overrideTemplate(WeddingServiceJoAngularComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingServiceJoAngularComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingServiceJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new WeddingServiceJoAngular(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.weddingServices[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
