import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WeddingServiceJoAngular } from './wedding-service-jo-angular.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WeddingServiceJoAngular>;

@Injectable()
export class WeddingServiceJoAngularService {

    private resourceUrl =  SERVER_API_URL + 'api/wedding-services';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(weddingService: WeddingServiceJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(weddingService);
        return this.http.post<WeddingServiceJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(weddingService: WeddingServiceJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(weddingService);
        return this.http.put<WeddingServiceJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WeddingServiceJoAngular>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WeddingServiceJoAngular[]>> {
        const options = createRequestOption(req);
        return this.http.get<WeddingServiceJoAngular[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WeddingServiceJoAngular[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WeddingServiceJoAngular = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WeddingServiceJoAngular[]>): HttpResponse<WeddingServiceJoAngular[]> {
        const jsonResponse: WeddingServiceJoAngular[] = res.body;
        const body: WeddingServiceJoAngular[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WeddingServiceJoAngular.
     */
    private convertItemFromServer(weddingService: WeddingServiceJoAngular): WeddingServiceJoAngular {
        const copy: WeddingServiceJoAngular = Object.assign({}, weddingService);
        copy.serviceCommittedDate = this.dateUtils
            .convertDateTimeFromServer(weddingService.serviceCommittedDate);
        return copy;
    }

    /**
     * Convert a WeddingServiceJoAngular to a JSON which can be sent to the server.
     */
    private convert(weddingService: WeddingServiceJoAngular): WeddingServiceJoAngular {
        const copy: WeddingServiceJoAngular = Object.assign({}, weddingService);

        copy.serviceCommittedDate = this.dateUtils.toDate(weddingService.serviceCommittedDate);
        return copy;
    }
}
