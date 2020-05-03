import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

type EntityResponseType = HttpResponse<IDoenteSocioFamiliar>;
type EntityArrayResponseType = HttpResponse<IDoenteSocioFamiliar[]>;

@Injectable({ providedIn: 'root' })
export class DoenteSocioFamiliarService {
  public resourceUrl = SERVER_API_URL + 'api/doente-socio-familiars';
  public resourceUrl2 = SERVER_API_URL + 'api/doente-socio-familiar';

  constructor(protected http: HttpClient) {}

  create(doenteSocioFamiliar: IDoenteSocioFamiliar): Observable<EntityResponseType> {
    return this.http.post<IDoenteSocioFamiliar>(this.resourceUrl, doenteSocioFamiliar, { observe: 'response' });
  }

  update(doenteSocioFamiliar: IDoenteSocioFamiliar): Observable<EntityResponseType> {
    return this.http.put<IDoenteSocioFamiliar>(this.resourceUrl, doenteSocioFamiliar, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteSocioFamiliar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteSocioFamiliar>(this.resourceUrl2 + '/?doente=' + id, { observe: 'response' });
}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoenteSocioFamiliar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
