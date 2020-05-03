import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';
import { DoenteSocioFamiliarService } from './doente-socio-familiar.service';
import { DoenteSocioFamiliarComponent } from './doente-socio-familiar.component';
import { DoenteSocioFamiliarDetailComponent } from './doente-socio-familiar-detail.component';
import { DoenteSocioFamiliarUpdateComponent } from './doente-socio-familiar-update.component';
import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

@Injectable({ providedIn: 'root' })
export class DoenteSocioFamiliarResolve implements Resolve<IDoenteSocioFamiliar> {
  constructor(private service: DoenteSocioFamiliarService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoenteSocioFamiliar> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doenteSocioFamiliar: HttpResponse<DoenteSocioFamiliar>) => doenteSocioFamiliar.body));
    }
    return of(new DoenteSocioFamiliar());
  }
}

export const doenteSocioFamiliarRoute: Routes = [
  {
    path: '',
    component: DoenteSocioFamiliarComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteSocioFamiliar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoenteSocioFamiliarDetailComponent,
    resolve: {
      doenteSocioFamiliar: DoenteSocioFamiliarResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteSocioFamiliar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoenteSocioFamiliarUpdateComponent,
    resolve: {
      doenteSocioFamiliar: DoenteSocioFamiliarResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteSocioFamiliar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoenteSocioFamiliarUpdateComponent,
    resolve: {
      doenteSocioFamiliar: DoenteSocioFamiliarResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteSocioFamiliar.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
