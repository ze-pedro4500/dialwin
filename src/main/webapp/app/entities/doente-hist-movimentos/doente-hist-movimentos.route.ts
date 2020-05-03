import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';
import { DoenteHistMovimentosService } from './doente-hist-movimentos.service';
import { DoenteHistMovimentosComponent } from './doente-hist-movimentos.component';
import { DoenteHistMovimentosDetailComponent } from './doente-hist-movimentos-detail.component';
import { DoenteHistMovimentosUpdateComponent } from './doente-hist-movimentos-update.component';
import { IDoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';

@Injectable({ providedIn: 'root' })
export class DoenteHistMovimentosResolve implements Resolve<IDoenteHistMovimentos> {
  constructor(private service: DoenteHistMovimentosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoenteHistMovimentos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doenteHistMovimentos: HttpResponse<DoenteHistMovimentos>) => doenteHistMovimentos.body));
    }
    return of(new DoenteHistMovimentos());
  }
}

export const doenteHistMovimentosRoute: Routes = [
  {
    path: '',
    component: DoenteHistMovimentosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteHistMovimentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoenteHistMovimentosDetailComponent,
    resolve: {
      doenteHistMovimentos: DoenteHistMovimentosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteHistMovimentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoenteHistMovimentosUpdateComponent,
    resolve: {
      doenteHistMovimentos: DoenteHistMovimentosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteHistMovimentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoenteHistMovimentosUpdateComponent,
    resolve: {
      doenteHistMovimentos: DoenteHistMovimentosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteHistMovimentos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
