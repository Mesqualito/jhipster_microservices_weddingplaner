/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { MessageJoAngularComponent } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular.component';
import { MessageJoAngularService } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular.service';
import { MessageJoAngular } from '../../../../../../main/webapp/app/entities/message-jo-angular/message-jo-angular.model';

describe('Component Tests', () => {

    describe('MessageJoAngular Management Component', () => {
        let comp: MessageJoAngularComponent;
        let fixture: ComponentFixture<MessageJoAngularComponent>;
        let service: MessageJoAngularService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [MessageJoAngularComponent],
                providers: [
                    MessageJoAngularService
                ]
            })
            .overrideTemplate(MessageJoAngularComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MessageJoAngularComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MessageJoAngularService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MessageJoAngular(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.messages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
