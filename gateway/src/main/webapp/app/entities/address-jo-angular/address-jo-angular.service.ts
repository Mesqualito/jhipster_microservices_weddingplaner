import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AddressJoAngular } from './address-jo-angular.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AddressJoAngular>;

@Injectable()
export class AddressJoAngularService {

    private resourceUrl =  SERVER_API_URL + 'api/addresses';

    constructor(private http: HttpClient) { }

    create(address: AddressJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(address);
        return this.http.post<AddressJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(address: AddressJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(address);
        return this.http.put<AddressJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AddressJoAngular>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AddressJoAngular[]>> {
        const options = createRequestOption(req);
        return this.http.get<AddressJoAngular[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AddressJoAngular[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AddressJoAngular = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AddressJoAngular[]>): HttpResponse<AddressJoAngular[]> {
        const jsonResponse: AddressJoAngular[] = res.body;
        const body: AddressJoAngular[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AddressJoAngular.
     */
    private convertItemFromServer(address: AddressJoAngular): AddressJoAngular {
        const copy: AddressJoAngular = Object.assign({}, address);
        return copy;
    }

    /**
     * Convert a AddressJoAngular to a JSON which can be sent to the server.
     */
    private convert(address: AddressJoAngular): AddressJoAngular {
        const copy: AddressJoAngular = Object.assign({}, address);
        return copy;
    }
}
