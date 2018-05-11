import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MessageJoAngular } from './message-jo-angular.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MessageJoAngular>;

@Injectable()
export class MessageJoAngularService {

    private resourceUrl =  SERVER_API_URL + 'api/messages';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(message: MessageJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(message);
        return this.http.post<MessageJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(message: MessageJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(message);
        return this.http.put<MessageJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MessageJoAngular>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MessageJoAngular[]>> {
        const options = createRequestOption(req);
        return this.http.get<MessageJoAngular[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MessageJoAngular[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MessageJoAngular = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MessageJoAngular[]>): HttpResponse<MessageJoAngular[]> {
        const jsonResponse: MessageJoAngular[] = res.body;
        const body: MessageJoAngular[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MessageJoAngular.
     */
    private convertItemFromServer(message: MessageJoAngular): MessageJoAngular {
        const copy: MessageJoAngular = Object.assign({}, message);
        copy.messageInitTime = this.dateUtils
            .convertDateTimeFromServer(message.messageInitTime);
        copy.messageValidFrom = this.dateUtils
            .convertLocalDateFromServer(message.messageValidFrom);
        copy.messageValidUntil = this.dateUtils
            .convertLocalDateFromServer(message.messageValidUntil);
        return copy;
    }

    /**
     * Convert a MessageJoAngular to a JSON which can be sent to the server.
     */
    private convert(message: MessageJoAngular): MessageJoAngular {
        const copy: MessageJoAngular = Object.assign({}, message);

        copy.messageInitTime = this.dateUtils.toDate(message.messageInitTime);
        copy.messageValidFrom = this.dateUtils
            .convertLocalDateToServer(message.messageValidFrom);
        copy.messageValidUntil = this.dateUtils
            .convertLocalDateToServer(message.messageValidUntil);
        return copy;
    }
}
