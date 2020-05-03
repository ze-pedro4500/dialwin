import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SubSistemas } from 'app/shared/model/sub-sistemas.model';
import { SubSistemasService } from './sub-sistemas.service';
import { SubSistemasComponent } from './sub-sistemas.component';
import { SubSistemasDetailComponent } from './sub-sistemas-detail.component';
import { SubSistemasUpdateComponent } from './sub-sistemas-update.component';
import { ISubSistemas } from 'app/shared/model/sub-sistemas.model';

@Injectable({ providedIn: 'root' })
export class SubSistemasResolve implements Resolve<ISubSistemas> {
  constructor(private service: SubSistemasService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubSistemas> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((subSistemas: HttpResponse<SubSistemas>) => subSistemas.body));
    }
    return of(new SubSistemas());
  }
}

export const subSistemasRoute: Routes = [
  {
    path: '',
    component: SubSistemasComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.subSistemas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubSistemasDetailComponent,
    resolve: {
      subSistemas: SubSistemasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.subSistemas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubSistemasUpdateComponent,
    resolve: {
      subSistemas: SubSistemasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.subSistemas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubSistemasUpdateComponent,
    resolve: {
      subSistemas: SubSistemasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.subSistemas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
