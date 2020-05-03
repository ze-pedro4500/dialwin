import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICentroSaude } from 'app/shared/model/centro-saude.model';

type EntityResponseType = HttpResponse<ICentroSaude>;
type EntityArrayResponseType = HttpResponse<ICentroSaude[]>;

@Injectable({ providedIn: 'root' })
export class CentroSaudeService {
  public resourceUrl = SERVER_API_URL + 'api/centro-saudes';

  constructor(protected http: HttpClient) {}

  create(centroSaude: ICentroSaude): Observable<EntityResponseType> {
    return this.http.post<ICentroSaude>(this.resourceUrl, centroSaude, { observe: 'response' });
  }

  update(centroSaude: ICentroSaude): Observable<EntityResponseType> {
    return this.http.put<ICentroSaude>(this.resourceUrl, centroSaude, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICentroSaude>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICentroSaude[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
