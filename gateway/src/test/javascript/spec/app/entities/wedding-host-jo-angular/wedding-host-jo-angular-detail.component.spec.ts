/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingHostJoAngularDetailComponent } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular-detail.component';
import { WeddingHostJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular.service';
import { WeddingHostJoAngular } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular.model';

describe('Component Tests', () => {

    describe('WeddingHostJoAngular Management Detail Component', () => {
        let comp: WeddingHostJoAngularDetailComponent;
        let fixture: ComponentFixture<WeddingHostJoAngularDetailComponent>;
        let service: WeddingHostJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingHostJoAngularDetailComponent],
                providers: [
                    WeddingHostJoAngularService
                ]
            })
            .overrideTemplate(WeddingHostJoAngularDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingHostJoAngularDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingHostJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new WeddingHostJoAngular(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.weddingHost).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
