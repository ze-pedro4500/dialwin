import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegistoData } from 'app/shared/model/registo-data.model';

type EntityResponseType = HttpResponse<IRegistoData>;
type EntityArrayResponseType = HttpResponse<IRegistoData[]>;

@Injectable({ providedIn: 'root' })
export class RegistoDataService {
  public resourceUrl = SERVER_API_URL + 'api/registo-data';

  constructor(protected http: HttpClient) {}

  create(registoData: IRegistoData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registoData);
    return this.http
      .post<IRegistoData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  search(registo: number): Observable<EntityResponseType> {
    return this.http.get<IRegistoData>(this.resourceUrl + '/?registo=' + registo, { observe: 'response' });
}

  update(registoData: IRegistoData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registoData);
    return this.http
      .put<IRegistoData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRegistoData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegistoData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(registoData: IRegistoData): IRegistoData {
    const copy: IRegistoData = Object.assign({}, registoData, {
      data: registoData.data && registoData.data.isValid() ? registoData.data.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((registoData: IRegistoData) => {
        registoData.data = registoData.data ? moment(registoData.data) : undefined;
      });
    }
    return res;
  }
}
