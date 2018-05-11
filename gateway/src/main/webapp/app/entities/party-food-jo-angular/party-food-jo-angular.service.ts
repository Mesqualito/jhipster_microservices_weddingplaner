import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PartyFoodJoAngular } from './party-food-jo-angular.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PartyFoodJoAngular>;

@Injectable()
export class PartyFoodJoAngularService {

    private resourceUrl =  SERVER_API_URL + 'api/party-foods';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(partyFood: PartyFoodJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(partyFood);
        return this.http.post<PartyFoodJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(partyFood: PartyFoodJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(partyFood);
        return this.http.put<PartyFoodJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PartyFoodJoAngular>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PartyFoodJoAngular[]>> {
        const options = createRequestOption(req);
        return this.http.get<PartyFoodJoAngular[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PartyFoodJoAngular[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PartyFoodJoAngular = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PartyFoodJoAngular[]>): HttpResponse<PartyFoodJoAngular[]> {
        const jsonResponse: PartyFoodJoAngular[] = res.body;
        const body: PartyFoodJoAngular[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PartyFoodJoAngular.
     */
    private convertItemFromServer(partyFood: PartyFoodJoAngular): PartyFoodJoAngular {
        const copy: PartyFoodJoAngular = Object.assign({}, partyFood);
        copy.foodBestServeTime = this.dateUtils
            .convertLocalDateFromServer(partyFood.foodBestServeTime);
        copy.foodProposalAcceptTime = this.dateUtils
            .convertDateTimeFromServer(partyFood.foodProposalAcceptTime);
        return copy;
    }

    /**
     * Convert a PartyFoodJoAngular to a JSON which can be sent to the server.
     */
    private convert(partyFood: PartyFoodJoAngular): PartyFoodJoAngular {
        const copy: PartyFoodJoAngular = Object.assign({}, partyFood);
        copy.foodBestServeTime = this.dateUtils
            .convertLocalDateToServer(partyFood.foodBestServeTime);

        copy.foodProposalAcceptTime = this.dateUtils.toDate(partyFood.foodProposalAcceptTime);
        return copy;
    }
}
