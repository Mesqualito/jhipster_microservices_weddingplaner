/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingHostJoAngularComponent } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular.component';
import { WeddingHostJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular.service';
import { WeddingHostJoAngular } from '../../../../../../main/webapp/app/entities/wedding-host-jo-angular/wedding-host-jo-angular.model';

describe('Component Tests', () => {

    describe('WeddingHostJoAngular Management Component', () => {
        let comp: WeddingHostJoAngularComponent;
        let fixture: ComponentFixture<WeddingHostJoAngularComponent>;
        let service: WeddingHostJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingHostJoAngularComponent],
                providers: [
                    WeddingHostJoAngularService
                ]
            })
            .overrideTemplate(WeddingHostJoAngularComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingHostJoAngularComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingHostJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new WeddingHostJoAngular(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.weddingHosts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
