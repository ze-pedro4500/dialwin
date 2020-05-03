import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Turnos } from 'app/shared/model/turnos.model';
import { TurnosService } from './turnos.service';
import { TurnosComponent } from './turnos.component';
import { TurnosDetailComponent } from './turnos-detail.component';
import { TurnosUpdateComponent } from './turnos-update.component';
import { ITurnos } from 'app/shared/model/turnos.model';

@Injectable({ providedIn: 'root' })
export class TurnosResolve implements Resolve<ITurnos> {
  constructor(private service: TurnosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITurnos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((turnos: HttpResponse<Turnos>) => turnos.body));
    }
    return of(new Turnos());
  }
}

export const turnosRoute: Routes = [
  {
    path: '',
    component: TurnosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.turnos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TurnosDetailComponent,
    resolve: {
      turnos: TurnosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.turnos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TurnosUpdateComponent,
    resolve: {
      turnos: TurnosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.turnos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TurnosUpdateComponent,
    resolve: {
      turnos: TurnosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.turnos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
