import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoenteContactos } from 'app/shared/model/doente-contactos.model';
import { DoenteContactosService } from './doente-contactos.service';
import { DoenteContactosComponent } from './doente-contactos.component';
import { DoenteContactosDetailComponent } from './doente-contactos-detail.component';
import { DoenteContactosUpdateComponent } from './doente-contactos-update.component';
import { IDoenteContactos } from 'app/shared/model/doente-contactos.model';

@Injectable({ providedIn: 'root' })
export class DoenteContactosResolve implements Resolve<IDoenteContactos> {
  constructor(private service: DoenteContactosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoenteContactos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doenteContactos: HttpResponse<DoenteContactos>) => doenteContactos.body));
    }
    return of(new DoenteContactos());
  }
}

export const doenteContactosRoute: Routes = [
  {
    path: '',
    component: DoenteContactosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteContactos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoenteContactosDetailComponent,
    resolve: {
      doenteContactos: DoenteContactosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteContactos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoenteContactosUpdateComponent,
    resolve: {
      doenteContactos: DoenteContactosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteContactos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoenteContactosUpdateComponent,
    resolve: {
      doenteContactos: DoenteContactosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteContactos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
