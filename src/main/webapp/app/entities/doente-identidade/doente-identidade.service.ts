import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';

type EntityResponseType = HttpResponse<IDoenteIdentidade>;
type EntityArrayResponseType = HttpResponse<IDoenteIdentidade[]>;

@Injectable({ providedIn: 'root' })
export class DoenteIdentidadeService {
  public resourceUrl = SERVER_API_URL + 'api/doente-identidades';
  public resourceUrl2 = SERVER_API_URL + 'api/doente-identidade';

  constructor(protected http: HttpClient) {}

  create(doenteIdentidade: IDoenteIdentidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doenteIdentidade);
    return this.http
      .post<IDoenteIdentidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(doenteIdentidade: IDoenteIdentidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doenteIdentidade);
    return this.http
      .put<IDoenteIdentidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDoenteIdentidade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  search(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenteIdentidade>(this.resourceUrl2 + '/?doente=' + id, { observe: 'response' });
}

  findByDoente(id: number): Observable<EntityResponseType> {
    return this.http
    .get<IDoenteIdentidade>(this.resourceUrl2, {observe: 'response'})
    .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDoenteIdentidade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(doenteIdentidade: IDoenteIdentidade): IDoenteIdentidade {
    const copy: IDoenteIdentidade = Object.assign({}, doenteIdentidade, {
      dataNasc:
        doenteIdentidade.dataNasc != null && doenteIdentidade.dataNasc.isValid() ? doenteIdentidade.dataNasc.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataNasc = res.body.dataNasc != null ? moment(res.body.dataNasc) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((doenteIdentidade: IDoenteIdentidade) => {
        doenteIdentidade.dataNasc = doenteIdentidade.dataNasc != null ? moment(doenteIdentidade.dataNasc) : null;
      });
    }
    return res;
  }
}
