import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PartyEventJoAngular } from './party-event-jo-angular.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PartyEventJoAngular>;

@Injectable()
export class PartyEventJoAngularService {

    private resourceUrl =  SERVER_API_URL + 'api/party-events';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(partyEvent: PartyEventJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(partyEvent);
        return this.http.post<PartyEventJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(partyEvent: PartyEventJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(partyEvent);
        return this.http.put<PartyEventJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PartyEventJoAngular>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PartyEventJoAngular[]>> {
        const options = createRequestOption(req);
        return this.http.get<PartyEventJoAngular[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PartyEventJoAngular[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PartyEventJoAngular = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PartyEventJoAngular[]>): HttpResponse<PartyEventJoAngular[]> {
        const jsonResponse: PartyEventJoAngular[] = res.body;
        const body: PartyEventJoAngular[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PartyEventJoAngular.
     */
    private convertItemFromServer(partyEvent: PartyEventJoAngular): PartyEventJoAngular {
        const copy: PartyEventJoAngular = Object.assign({}, partyEvent);
        copy.eventInitTime = this.dateUtils
            .convertDateTimeFromServer(partyEvent.eventInitTime);
        copy.eventStartTime = this.dateUtils
            .convertLocalDateFromServer(partyEvent.eventStartTime);
        copy.eventEndTime = this.dateUtils
            .convertLocalDateFromServer(partyEvent.eventEndTime);
        return copy;
    }

    /**
     * Convert a PartyEventJoAngular to a JSON which can be sent to the server.
     */
    private convert(partyEvent: PartyEventJoAngular): PartyEventJoAngular {
        const copy: PartyEventJoAngular = Object.assign({}, partyEvent);

        copy.eventInitTime = this.dateUtils.toDate(partyEvent.eventInitTime);
        copy.eventStartTime = this.dateUtils
            .convertLocalDateToServer(partyEvent.eventStartTime);
        copy.eventEndTime = this.dateUtils
            .convertLocalDateToServer(partyEvent.eventEndTime);
        return copy;
    }
}
