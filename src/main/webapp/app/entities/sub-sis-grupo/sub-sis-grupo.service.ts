import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';

type EntityResponseType = HttpResponse<ISubSisGrupo>;
type EntityArrayResponseType = HttpResponse<ISubSisGrupo[]>;

@Injectable({ providedIn: 'root' })
export class SubSisGrupoService {
  public resourceUrl = SERVER_API_URL + 'api/sub-sis-grupos';

  constructor(protected http: HttpClient) {}

  create(subSisGrupo: ISubSisGrupo): Observable<EntityResponseType> {
    return this.http.post<ISubSisGrupo>(this.resourceUrl, subSisGrupo, { observe: 'response' });
  }

  update(subSisGrupo: ISubSisGrupo): Observable<EntityResponseType> {
    return this.http.put<ISubSisGrupo>(this.resourceUrl, subSisGrupo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubSisGrupo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubSisGrupo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
