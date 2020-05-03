import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Habit } from 'app/shared/model/habit.model';
import { HabitService } from './habit.service';
import { HabitComponent } from './habit.component';
import { HabitDetailComponent } from './habit-detail.component';
import { HabitUpdateComponent } from './habit-update.component';
import { IHabit } from 'app/shared/model/habit.model';

@Injectable({ providedIn: 'root' })
export class HabitResolve implements Resolve<IHabit> {
  constructor(private service: HabitService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHabit> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((habit: HttpResponse<Habit>) => habit.body));
    }
    return of(new Habit());
  }
}

export const habitRoute: Routes = [
  {
    path: '',
    component: HabitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.habit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HabitDetailComponent,
    resolve: {
      habit: HabitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.habit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HabitUpdateComponent,
    resolve: {
      habit: HabitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.habit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HabitUpdateComponent,
    resolve: {
      habit: HabitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.habit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
