import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AppUserJoAngular } from './app-user-jo-angular.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AppUserJoAngular>;

@Injectable()
export class AppUserJoAngularService {

    private resourceUrl =  SERVER_API_URL + 'api/app-users';

    constructor(private http: HttpClient) { }

    create(appUser: AppUserJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(appUser);
        return this.http.post<AppUserJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(appUser: AppUserJoAngular): Observable<EntityResponseType> {
        const copy = this.convert(appUser);
        return this.http.put<AppUserJoAngular>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AppUserJoAngular>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AppUserJoAngular[]>> {
        const options = createRequestOption(req);
        return this.http.get<AppUserJoAngular[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AppUserJoAngular[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AppUserJoAngular = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AppUserJoAngular[]>): HttpResponse<AppUserJoAngular[]> {
        const jsonResponse: AppUserJoAngular[] = res.body;
        const body: AppUserJoAngular[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AppUserJoAngular.
     */
    private convertItemFromServer(appUser: AppUserJoAngular): AppUserJoAngular {
        const copy: AppUserJoAngular = Object.assign({}, appUser);
        return copy;
    }

    /**
     * Convert a AppUserJoAngular to a JSON which can be sent to the server.
     */
    private convert(appUser: AppUserJoAngular): AppUserJoAngular {
        const copy: AppUserJoAngular = Object.assign({}, appUser);
        return copy;
    }
}
