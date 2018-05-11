/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingServiceJoAngularDetailComponent } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular-detail.component';
import { WeddingServiceJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular.service';
import { WeddingServiceJoAngular } from '../../../../../../main/webapp/app/entities/wedding-service-jo-angular/wedding-service-jo-angular.model';

describe('Component Tests', () => {

    describe('WeddingServiceJoAngular Management Detail Component', () => {
        let comp: WeddingServiceJoAngularDetailComponent;
        let fixture: ComponentFixture<WeddingServiceJoAngularDetailComponent>;
        let service: WeddingServiceJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingServiceJoAngularDetailComponent],
                providers: [
                    WeddingServiceJoAngularService
                ]
            })
            .overrideTemplate(WeddingServiceJoAngularDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingServiceJoAngularDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingServiceJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new WeddingServiceJoAngular(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.weddingService).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
