import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubSistemas } from 'app/shared/model/sub-sistemas.model';

type EntityResponseType = HttpResponse<ISubSistemas>;
type EntityArrayResponseType = HttpResponse<ISubSistemas[]>;

@Injectable({ providedIn: 'root' })
export class SubSistemasService {
  public resourceUrl = SERVER_API_URL + 'api/sub-sistemas';

  constructor(protected http: HttpClient) {}

  create(subSistemas: ISubSistemas): Observable<EntityResponseType> {
    return this.http.post<ISubSistemas>(this.resourceUrl, subSistemas, { observe: 'response' });
  }

  update(subSistemas: ISubSistemas): Observable<EntityResponseType> {
    return this.http.put<ISubSistemas>(this.resourceUrl, subSistemas, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubSistemas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubSistemas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
