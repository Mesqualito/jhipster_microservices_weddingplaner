/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyEventJoAngularComponent } from '../../../../../../main/webapp/app/entities/party-event-jo-angular/party-event-jo-angular.component';
import { PartyEventJoAngularService } from '../../../../../../main/webapp/app/entities/party-event-jo-angular/party-event-jo-angular.service';
import { PartyEventJoAngular } from '../../../../../../main/webapp/app/entities/party-event-jo-angular/party-event-jo-angular.model';

describe('Component Tests', () => {

    describe('PartyEventJoAngular Management Component', () => {
        let comp: PartyEventJoAngularComponent;
        let fixture: ComponentFixture<PartyEventJoAngularComponent>;
        let service: PartyEventJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyEventJoAngularComponent],
                providers: [
                    PartyEventJoAngularService
                ]
            })
            .overrideTemplate(PartyEventJoAngularComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyEventJoAngularComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyEventJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PartyEventJoAngular(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.partyEvents[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
