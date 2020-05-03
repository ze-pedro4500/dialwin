import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';
import { DoenteContactosOutrosService } from './doente-contactos-outros.service';
import { DoenteContactosOutrosComponent } from './doente-contactos-outros.component';
import { DoenteContactosOutrosDetailComponent } from './doente-contactos-outros-detail.component';
import { DoenteContactosOutrosUpdateComponent } from './doente-contactos-outros-update.component';
import { IDoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';

@Injectable({ providedIn: 'root' })
export class DoenteContactosOutrosResolve implements Resolve<IDoenteContactosOutros> {
  constructor(private service: DoenteContactosOutrosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoenteContactosOutros> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doenteContactosOutros: HttpResponse<DoenteContactosOutros>) => doenteContactosOutros.body));
    }
    return of(new DoenteContactosOutros());
  }
}

export const doenteContactosOutrosRoute: Routes = [
  {
    path: '',
    component: DoenteContactosOutrosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteContactosOutros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoenteContactosOutrosDetailComponent,
    resolve: {
      doenteContactosOutros: DoenteContactosOutrosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteContactosOutros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoenteContactosOutrosUpdateComponent,
    resolve: {
      doenteContactosOutros: DoenteContactosOutrosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteContactosOutros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoenteContactosOutrosUpdateComponent,
    resolve: {
      doenteContactosOutros: DoenteContactosOutrosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteContactosOutros.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
