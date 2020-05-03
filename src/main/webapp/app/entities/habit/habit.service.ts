import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHabit } from 'app/shared/model/habit.model';

type EntityResponseType = HttpResponse<IHabit>;
type EntityArrayResponseType = HttpResponse<IHabit[]>;

@Injectable({ providedIn: 'root' })
export class HabitService {
  public resourceUrl = SERVER_API_URL + 'api/habits';

  constructor(protected http: HttpClient) {}

  create(habit: IHabit): Observable<EntityResponseType> {
    return this.http.post<IHabit>(this.resourceUrl, habit, { observe: 'response' });
  }

  update(habit: IHabit): Observable<EntityResponseType> {
    return this.http.put<IHabit>(this.resourceUrl, habit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHabit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHabit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
