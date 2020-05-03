import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';

type EntityResponseType = HttpResponse<IDoenteRegistosIntervencoes>;
type EntityArrayResponseType = HttpResponse<IDoenteRegistosIntervencoes[]>;

@Injectable({ providedIn: 'root' })
export class DoenteRegistosIntervencoesService {
  public resourceUrl = SERVER_API_URL + 'api/doente-registos-intervencoes';

  constructor(protected http: HttpClient) {}

  create(doenteRegistosIntervencoes: IDoenteRegistosIntervencoes): Observable<EntityResponseType> {
    return this.http.post<IDoenteRegistosIntervencoes>(this.resourceUrl, doenteRegistosIntervencoes, { observe: 'response' });
  }

  update(doenteRegistosIntervencoes: IDoenteRegistosIntervencoes): Observable<EntityResponseType> {
    return this.http.put<IDoenteRegistosIntervencoes>(this.resourceUrl, doenteRegistosIntervencoes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteRegistosIntervencoes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IDoenteRegistosIntervencoes[]>(this.resourceUrl + '/?doente=' + id, { observe: 'response' });
}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoenteRegistosIntervencoes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
