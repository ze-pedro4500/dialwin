import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';

type EntityResponseType = HttpResponse<IDoenteDiagnosticoSocial>;
type EntityArrayResponseType = HttpResponse<IDoenteDiagnosticoSocial[]>;

@Injectable({ providedIn: 'root' })
export class DoenteDiagnosticoSocialService {
  public resourceUrl = SERVER_API_URL + 'api/doente-diagnostico-socials';
  public resourceUrl2 = SERVER_API_URL + 'api/doente-diagnostico-social';

  constructor(protected http: HttpClient) {}

  create(doenteDiagnosticoSocial: IDoenteDiagnosticoSocial): Observable<EntityResponseType> {
    return this.http.post<IDoenteDiagnosticoSocial>(this.resourceUrl, doenteDiagnosticoSocial, { observe: 'response' });
  }

  update(doenteDiagnosticoSocial: IDoenteDiagnosticoSocial): Observable<EntityResponseType> {
    return this.http.put<IDoenteDiagnosticoSocial>(this.resourceUrl, doenteDiagnosticoSocial, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteDiagnosticoSocial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoenteDiagnosticoSocial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
 
  search(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteDiagnosticoSocial>(this.resourceUrl2 + '/?doente=' + id, { observe: 'response' });
}

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
