import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoente } from 'app/shared/model/doente.model';

type EntityResponseType = HttpResponse<IDoente>;
type EntityArrayResponseType = HttpResponse<IDoente[]>;

@Injectable({ providedIn: 'root' })
export class DoenteService {
  public resourceUrl = SERVER_API_URL + 'api/doentes';

  constructor(protected http: HttpClient) {}

  create(doente: IDoente): Observable<EntityResponseType> {
    return this.http.post<IDoente>(this.resourceUrl, doente, { observe: 'response' });
  }

  update(doente: IDoente): Observable<EntityResponseType> {
    return this.http.put<IDoente>(this.resourceUrl, doente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  search(situacao: any, sub: any, turno: any): Observable<EntityArrayResponseType> {
    return this.http.get<IDoente[]>(this.resourceUrl + '/?situacao=' + situacao + '&sub=' + sub + '&t=' + turno, { observe: 'response' });
}

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
