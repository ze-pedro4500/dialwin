import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGrauConf } from 'app/shared/model/grau-conf.model';

type EntityResponseType = HttpResponse<IGrauConf>;
type EntityArrayResponseType = HttpResponse<IGrauConf[]>;

@Injectable({ providedIn: 'root' })
export class GrauConfService {
  public resourceUrl = SERVER_API_URL + 'api/grau-confs';

  constructor(protected http: HttpClient) {}

  create(grauConf: IGrauConf): Observable<EntityResponseType> {
    return this.http.post<IGrauConf>(this.resourceUrl, grauConf, { observe: 'response' });
  }

  update(grauConf: IGrauConf): Observable<EntityResponseType> {
    return this.http.put<IGrauConf>(this.resourceUrl, grauConf, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrauConf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrauConf[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
