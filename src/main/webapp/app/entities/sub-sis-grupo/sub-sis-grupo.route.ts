import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';
import { SubSisGrupoService } from './sub-sis-grupo.service';
import { SubSisGrupoComponent } from './sub-sis-grupo.component';
import { SubSisGrupoDetailComponent } from './sub-sis-grupo-detail.component';
import { SubSisGrupoUpdateComponent } from './sub-sis-grupo-update.component';
import { ISubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';

@Injectable({ providedIn: 'root' })
export class SubSisGrupoResolve implements Resolve<ISubSisGrupo> {
  constructor(private service: SubSisGrupoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubSisGrupo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((subSisGrupo: HttpResponse<SubSisGrupo>) => subSisGrupo.body));
    }
    return of(new SubSisGrupo());
  }
}

export const subSisGrupoRoute: Routes = [
  {
    path: '',
    component: SubSisGrupoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.subSisGrupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubSisGrupoDetailComponent,
    resolve: {
      subSisGrupo: SubSisGrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.subSisGrupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubSisGrupoUpdateComponent,
    resolve: {
      subSisGrupo: SubSisGrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.subSisGrupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubSisGrupoUpdateComponent,
    resolve: {
      subSisGrupo: SubSisGrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.subSisGrupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
