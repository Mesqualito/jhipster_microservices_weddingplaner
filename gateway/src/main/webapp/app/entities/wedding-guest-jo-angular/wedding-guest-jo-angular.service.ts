import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WeddingGuestJoAngular } from './wedding-guest-jo-angular.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WeddingGuestJoAngular>;

@Injectable()
export class WeddingGuestJoAngularService {

    private resourceUrl =  SERVER_API_URL + 'api/wedding-guests';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(weddingGuest: WeddingGuestJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(weddingGuest);
        return this.http.post<WeddingGuestJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(weddingGuest: WeddingGuestJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(weddingGuest);
        return this.http.put<WeddingGuestJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WeddingGuestJoAngular>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WeddingGuestJoAngular[]>> {
        const options = createRequestOption(req);
        return this.http.get<WeddingGuestJoAngular[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WeddingGuestJoAngular[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WeddingGuestJoAngular = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WeddingGuestJoAngular[]>): HttpResponse<WeddingGuestJoAngular[]> {
        const jsonResponse: WeddingGuestJoAngular[] = res.body;
        const body: WeddingGuestJoAngular[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WeddingGuestJoAngular.
     */
    private convertItemFromServer(weddingGuest: WeddingGuestJoAngular): WeddingGuestJoAngular {
        const copy: WeddingGuestJoAngular = Object.assign({}, weddingGuest);
        copy.guestInvitationDate = this.dateUtils
            .convertDateTimeFromServer(weddingGuest.guestInvitationDate);
        copy.guestRsvpDate = this.dateUtils
            .convertDateTimeFromServer(weddingGuest.guestRsvpDate);
        return copy;
    }

    /**
     * Convert a WeddingGuestJoAngular to a JSON which can be sent to the server.
     */
    private convert(weddingGuest: WeddingGuestJoAngular): WeddingGuestJoAngular {
        const copy: WeddingGuestJoAngular = Object.assign({}, weddingGuest);

        copy.guestInvitationDate = this.dateUtils.toDate(weddingGuest.guestInvitationDate);

        copy.guestRsvpDate = this.dateUtils.toDate(weddingGuest.guestRsvpDate);
        return copy;
    }
}
