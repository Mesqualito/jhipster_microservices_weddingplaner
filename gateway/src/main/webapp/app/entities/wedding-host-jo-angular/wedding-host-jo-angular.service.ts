import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WeddingHostJoAngular } from './wedding-host-jo-angular.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WeddingHostJoAngular>;

@Injectable()
export class WeddingHostJoAngularService {

    private resourceUrl =  SERVER_API_URL + 'api/wedding-hosts';

    constructor(private http: HttpClient) { }

    create(weddingHost: WeddingHostJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(weddingHost);
        return this.http.post<WeddingHostJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(weddingHost: WeddingHostJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(weddingHost);
        return this.http.put<WeddingHostJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WeddingHostJoAngular>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WeddingHostJoAngular[]>> {
        const options = createRequestOption(req);
        return this.http.get<WeddingHostJoAngular[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WeddingHostJoAngular[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WeddingHostJoAngular = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WeddingHostJoAngular[]>): HttpResponse<WeddingHostJoAngular[]> {
        const jsonResponse: WeddingHostJoAngular[] = res.body;
        const body: WeddingHostJoAngular[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WeddingHostJoAngular.
     */
    private convertItemFromServer(weddingHost: WeddingHostJoAngular): WeddingHostJoAngular {
        const copy: WeddingHostJoAngular = Object.assign({}, weddingHost);
        return copy;
    }

    /**
     * Convert a WeddingHostJoAngular to a JSON which can be sent to the server.
     */
    private convert(weddingHost: WeddingHostJoAngular): WeddingHostJoAngular {
        const copy: WeddingHostJoAngular = Object.assign({}, weddingHost);
        return copy;
    }
}
