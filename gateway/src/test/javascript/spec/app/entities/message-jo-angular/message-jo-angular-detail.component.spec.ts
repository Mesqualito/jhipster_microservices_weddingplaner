/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { MessageJoAngularDetailComponent } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular-detail.component';
import { MessageJoAngularService } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular.service';
import { MessageJoAngular } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular.model';

describe('Component Tests', () => {

    describe('MessageJoAngular Management Detail Component', () => {
        let comp: MessageJoAngularDetailComponent;
        let fixture: ComponentFixture<MessageJoAngularDetailComponent>;
        let service: MessageJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [MessageJoAngularDetailComponent],
                providers: [
                    MessageJoAngularService
                ]
            })
            .overrideTemplate(MessageJoAngularDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MessageJoAngularDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MessageJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MessageJoAngular(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.message).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
