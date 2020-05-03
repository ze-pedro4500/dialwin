import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Doente } from 'app/shared/model/doente.model';
import { DoenteService } from './doente.service';
import { DoenteComponent } from './doente.component';
import { DoenteDetailComponent } from './doente-detail.component';
import { DoenteUpdateComponent } from './doente-update.component';
import { IDoente } from 'app/shared/model/doente.model';

@Injectable({ providedIn: 'root' })
export class DoenteResolve implements Resolve<IDoente> {
  constructor(private service: DoenteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doente: HttpResponse<Doente>) => doente.body));
    }
    return of(new Doente());
  }
}

export const doenteRoute: Routes = [
  {
    path: '',
    component: DoenteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoenteDetailComponent,
    resolve: {
      doente: DoenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoenteUpdateComponent,
    resolve: {
      doente: DoenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoenteUpdateComponent,
    resolve: {
      doente: DoenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
