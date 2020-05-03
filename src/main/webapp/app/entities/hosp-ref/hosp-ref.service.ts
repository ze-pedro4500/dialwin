import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHospRef } from 'app/shared/model/hosp-ref.model';

type EntityResponseType = HttpResponse<IHospRef>;
type EntityArrayResponseType = HttpResponse<IHospRef[]>;

@Injectable({ providedIn: 'root' })
export class HospRefService {
  public resourceUrl = SERVER_API_URL + 'api/hosp-refs';

  constructor(protected http: HttpClient) {}

  create(hospRef: IHospRef): Observable<EntityResponseType> {
    return this.http.post<IHospRef>(this.resourceUrl, hospRef, { observe: 'response' });
  }

  update(hospRef: IHospRef): Observable<EntityResponseType> {
    return this.http.put<IHospRef>(this.resourceUrl, hospRef, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHospRef>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHospRef[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
