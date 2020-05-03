import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoenteContactos } from 'app/shared/model/doente-contactos.model';

type EntityResponseType = HttpResponse<IDoenteContactos>;
type EntityArrayResponseType = HttpResponse<IDoenteContactos[]>;

@Injectable({ providedIn: 'root' })
export class DoenteContactosService {
  public resourceUrl = SERVER_API_URL + 'api/doente-contactos';

  constructor(protected http: HttpClient) {}

  create(doenteContactos: IDoenteContactos): Observable<EntityResponseType> {
    return this.http.post<IDoenteContactos>(this.resourceUrl, doenteContactos, { observe: 'response' });
  }

  update(doenteContactos: IDoenteContactos): Observable<EntityResponseType> {
    return this.http.put<IDoenteContactos>(this.resourceUrl, doenteContactos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteContactos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoenteContactos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  search(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteContactos>(this.resourceUrl + '/?doente=' + id, { observe: 'response' });
}

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
