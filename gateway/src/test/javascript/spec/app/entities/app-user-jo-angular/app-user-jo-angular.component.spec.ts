/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { AppUserJoAngularComponent } from '../../../../../../main/webapp/app/entities/app-user-jo-angular/app-user-jo-angular.component';
import { AppUserJoAngularService } from '../../../../../../main/webapp/app/entities/app-user-jo-angular/app-user-jo-angular.service';
import { AppUserJoAngular } from '../../../../../../main/webapp/app/entities/app-user-jo-angular/app-user-jo-angular.model';

describe('Component Tests', () => {

    describe('AppUserJoAngular Management Component', () => {
        let comp: AppUserJoAngularComponent;
        let fixture: ComponentFixture<AppUserJoAngularComponent>;
        let service: AppUserJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AppUserJoAngularComponent],
                providers: [
                    AppUserJoAngularService
                ]
            })
            .overrideTemplate(AppUserJoAngularComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppUserJoAngularComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppUserJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AppUserJoAngular(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.appUsers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
