/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyEventJoAngularDetailComponent } from '../../../../../../main/webapp/app/entities/party-event-jo-angular/party-event-jo-angular-detail.component';
import { PartyEventJoAngularService } from '../../../../../../main/webapp/app/entities/party-event-jo-angular/party-event-jo-angular.service';
import { PartyEventJoAngular } from '../../../../../../main/webapp/app/entities/party-event-jo-angular/party-event-jo-angular.model';

describe('Component Tests', () => {

    describe('PartyEventJoAngular Management Detail Component', () => {
        let comp: PartyEventJoAngularDetailComponent;
        let fixture: ComponentFixture<PartyEventJoAngularDetailComponent>;
        let service: PartyEventJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyEventJoAngularDetailComponent],
                providers: [
                    PartyEventJoAngularService
                ]
            })
            .overrideTemplate(PartyEventJoAngularDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyEventJoAngularDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyEventJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PartyEventJoAngular(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.partyEvent).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
