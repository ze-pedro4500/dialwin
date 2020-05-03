import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';

type EntityResponseType = HttpResponse<IDoenteContactosOutros>;
type EntityArrayResponseType = HttpResponse<IDoenteContactosOutros[]>;

@Injectable({ providedIn: 'root' })
export class DoenteContactosOutrosService {
  public resourceUrl = SERVER_API_URL + 'api/doente-contactos-outros';

  constructor(protected http: HttpClient) {}

  create(doenteContactosOutros: IDoenteContactosOutros): Observable<EntityResponseType> {
    return this.http.post<IDoenteContactosOutros>(this.resourceUrl, doenteContactosOutros, { observe: 'response' });
  }

  update(doenteContactosOutros: IDoenteContactosOutros): Observable<EntityResponseType> {
    return this.http.put<IDoenteContactosOutros>(this.resourceUrl, doenteContactosOutros, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteContactosOutros>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IDoenteContactosOutros[]>(this.resourceUrl + '/?doente=' + id, { observe: 'response' });
}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoenteContactosOutros[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(this.resourceUrl+"/"+id, { observe: 'response' });
  }
}
