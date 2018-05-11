/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyFoodJoAngularDetailComponent } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular-detail.component';
import { PartyFoodJoAngularService } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular.service';
import { PartyFoodJoAngular } from '../../../../../../main/webapp/app/entities/party-food-jo-angular/party-food-jo-angular.model';

describe('Component Tests', () => {

    describe('PartyFoodJoAngular Management Detail Component', () => {
        let comp: PartyFoodJoAngularDetailComponent;
        let fixture: ComponentFixture<PartyFoodJoAngularDetailComponent>;
        let service: PartyFoodJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyFoodJoAngularDetailComponent],
                providers: [
                    PartyFoodJoAngularService
                ]
            })
            .overrideTemplate(PartyFoodJoAngularDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyFoodJoAngularDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyFoodJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PartyFoodJoAngular(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.partyFood).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
