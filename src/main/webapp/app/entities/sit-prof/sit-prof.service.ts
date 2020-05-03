import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISitProf } from 'app/shared/model/sit-prof.model';

type EntityResponseType = HttpResponse<ISitProf>;
type EntityArrayResponseType = HttpResponse<ISitProf[]>;

@Injectable({ providedIn: 'root' })
export class SitProfService {
  public resourceUrl = SERVER_API_URL + 'api/sit-profs';

  constructor(protected http: HttpClient) {}

  create(sitProf: ISitProf): Observable<EntityResponseType> {
    return this.http.post<ISitProf>(this.resourceUrl, sitProf, { observe: 'response' });
  }

  update(sitProf: ISitProf): Observable<EntityResponseType> {
    return this.http.put<ISitProf>(this.resourceUrl, sitProf, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISitProf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISitProf[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
