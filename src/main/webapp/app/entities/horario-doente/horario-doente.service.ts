import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHorarioDoente } from 'app/shared/model/horario-doente.model';

type EntityResponseType = HttpResponse<IHorarioDoente>;
type EntityArrayResponseType = HttpResponse<IHorarioDoente[]>;

@Injectable({ providedIn: 'root' })
export class HorarioDoenteService {
  public resourceUrl = SERVER_API_URL + 'api/horario-doentes';

  constructor(protected http: HttpClient) {}

  create(horarioDoente: IHorarioDoente): Observable<EntityResponseType> {
    return this.http.post<IHorarioDoente>(this.resourceUrl, horarioDoente, { observe: 'response' });
  }

  update(horarioDoente: IHorarioDoente): Observable<EntityResponseType> {
    return this.http.put<IHorarioDoente>(this.resourceUrl, horarioDoente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHorarioDoente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHorarioDoente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  search(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IHorarioDoente[]>(this.resourceUrl + '/?doente=' + id, { observe: 'response' });
}

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
