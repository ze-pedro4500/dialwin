import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVitalidade } from 'app/shared/model/vitalidade.model';

type EntityResponseType = HttpResponse<IVitalidade>;
type EntityArrayResponseType = HttpResponse<IVitalidade[]>;

@Injectable({ providedIn: 'root' })
export class VitalidadeService {
  public resourceUrl = SERVER_API_URL + 'api/vitalidades';

  constructor(protected http: HttpClient) {}

  create(vitalidade: IVitalidade): Observable<EntityResponseType> {
    return this.http.post<IVitalidade>(this.resourceUrl, vitalidade, { observe: 'response' });
  }

  update(vitalidade: IVitalidade): Observable<EntityResponseType> {
    return this.http.put<IVitalidade>(this.resourceUrl, vitalidade, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVitalidade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVitalidade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
