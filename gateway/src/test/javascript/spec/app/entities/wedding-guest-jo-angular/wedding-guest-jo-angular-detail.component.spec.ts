/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { WeddingGuestJoAngularDetailComponent } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular-detail.component';
import { WeddingGuestJoAngularService } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular.service';
import { WeddingGuestJoAngular } from '../../../../../../main/webapp/app/entities/wedding-guest-jo-angular/wedding-guest-jo-angular.model';

describe('Component Tests', () => {

    describe('WeddingGuestJoAngular Management Detail Component', () => {
        let comp: WeddingGuestJoAngularDetailComponent;
        let fixture: ComponentFixture<WeddingGuestJoAngularDetailComponent>;
        let service: WeddingGuestJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [WeddingGuestJoAngularDetailComponent],
                providers: [
                    WeddingGuestJoAngularService
                ]
            })
            .overrideTemplate(WeddingGuestJoAngularDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeddingGuestJoAngularDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeddingGuestJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new WeddingGuestJoAngular(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.weddingGuest).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
