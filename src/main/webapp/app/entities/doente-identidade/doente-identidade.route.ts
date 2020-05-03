import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoenteIdentidade } from 'app/shared/model/doente-identidade.model';
import { DoenteIdentidadeService } from './doente-identidade.service';
import { DoenteIdentidadeComponent } from './doente-identidade.component';
import { DoenteIdentidadeDetailComponent } from './doente-identidade-detail.component';
import { DoenteIdentidadeUpdateComponent } from './doente-identidade-update.component';
import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';

@Injectable({ providedIn: 'root' })
export class DoenteIdentidadeResolve implements Resolve<IDoenteIdentidade> {
  constructor(private service: DoenteIdentidadeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoenteIdentidade> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doenteIdentidade: HttpResponse<DoenteIdentidade>) => doenteIdentidade.body));
    }
    return of(new DoenteIdentidade());
  }
}

export const doenteIdentidadeRoute: Routes = [
  {
    path: '',
    component: DoenteIdentidadeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteIdentidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoenteIdentidadeDetailComponent,
    resolve: {
      doenteIdentidade: DoenteIdentidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteIdentidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoenteIdentidadeUpdateComponent,
    resolve: {
      doenteIdentidade: DoenteIdentidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteIdentidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoenteIdentidadeUpdateComponent,
    resolve: {
      doenteIdentidade: DoenteIdentidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteIdentidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
