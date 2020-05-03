import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HorarioDoente } from 'app/shared/model/horario-doente.model';
import { HorarioDoenteService } from './horario-doente.service';
import { HorarioDoenteComponent } from './horario-doente.component';
import { HorarioDoenteDetailComponent } from './horario-doente-detail.component';
import { HorarioDoenteUpdateComponent } from './horario-doente-update.component';
import { IHorarioDoente } from 'app/shared/model/horario-doente.model';

@Injectable({ providedIn: 'root' })
export class HorarioDoenteResolve implements Resolve<IHorarioDoente> {
  constructor(private service: HorarioDoenteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHorarioDoente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((horarioDoente: HttpResponse<HorarioDoente>) => horarioDoente.body));
    }
    return of(new HorarioDoente());
  }
}

export const horarioDoenteRoute: Routes = [
  {
    path: '',
    component: HorarioDoenteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.horarioDoente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HorarioDoenteDetailComponent,
    resolve: {
      horarioDoente: HorarioDoenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.horarioDoente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HorarioDoenteUpdateComponent,
    resolve: {
      horarioDoente: HorarioDoenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.horarioDoente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HorarioDoenteUpdateComponent,
    resolve: {
      horarioDoente: HorarioDoenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.horarioDoente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
