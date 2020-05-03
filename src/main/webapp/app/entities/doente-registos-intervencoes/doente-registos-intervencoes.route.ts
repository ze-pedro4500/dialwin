import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';
import { DoenteRegistosIntervencoesService } from './doente-registos-intervencoes.service';
import { DoenteRegistosIntervencoesComponent } from './doente-registos-intervencoes.component';
import { DoenteRegistosIntervencoesDetailComponent } from './doente-registos-intervencoes-detail.component';
import { DoenteRegistosIntervencoesUpdateComponent } from './doente-registos-intervencoes-update.component';
import { IDoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';

@Injectable({ providedIn: 'root' })
export class DoenteRegistosIntervencoesResolve implements Resolve<IDoenteRegistosIntervencoes> {
  constructor(private service: DoenteRegistosIntervencoesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoenteRegistosIntervencoes> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((doenteRegistosIntervencoes: HttpResponse<DoenteRegistosIntervencoes>) => doenteRegistosIntervencoes.body));
    }
    return of(new DoenteRegistosIntervencoes());
  }
}

export const doenteRegistosIntervencoesRoute: Routes = [
  {
    path: '',
    component: DoenteRegistosIntervencoesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteRegistosIntervencoes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoenteRegistosIntervencoesDetailComponent,
    resolve: {
      doenteRegistosIntervencoes: DoenteRegistosIntervencoesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteRegistosIntervencoes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoenteRegistosIntervencoesUpdateComponent,
    resolve: {
      doenteRegistosIntervencoes: DoenteRegistosIntervencoesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteRegistosIntervencoes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoenteRegistosIntervencoesUpdateComponent,
    resolve: {
      doenteRegistosIntervencoes: DoenteRegistosIntervencoesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteRegistosIntervencoes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
