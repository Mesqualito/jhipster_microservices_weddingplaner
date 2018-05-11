/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingGuestJoAngularComponent } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular.component';
import { WeddingGuestJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular.service';
import { WeddingGuestJoAngular } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular.model';

describe('Component Tests', () => {

    describe('WeddingGuestJoAngular Management Component', () => {
        let comp: WeddingGuestJoAngularComponent;
        let fixture: ComponentFixture<WeddingGuestJoAngularComponent>;
        let service: WeddingGuestJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingGuestJoAngularComponent],
                providers: [
                    WeddingGuestJoAngularService
                ]
            })
            .overrideTemplate(WeddingGuestJoAngularComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingGuestJoAngularComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingGuestJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new WeddingGuestJoAngular(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.weddingGuests[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
