/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyFoodJoAngularComponent } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular.component';
import { PartyFoodJoAngularService } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular.service';
import { PartyFoodJoAngular } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular.model';

describe('Component Tests', () => {

    describe('PartyFoodJoAngular Management Component', () => {
        let comp: PartyFoodJoAngularComponent;
        let fixture: ComponentFixture<PartyFoodJoAngularComponent>;
        let service: PartyFoodJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyFoodJoAngularComponent],
                providers: [
                    PartyFoodJoAngularService
                ]
            })
            .overrideTemplate(PartyFoodJoAngularComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyFoodJoAngularComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyFoodJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PartyFoodJoAngular(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.partyFoods[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
