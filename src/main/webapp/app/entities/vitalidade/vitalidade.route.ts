import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Vitalidade } from 'app/shared/model/vitalidade.model';
import { VitalidadeService } from './vitalidade.service';
import { VitalidadeComponent } from './vitalidade.component';
import { VitalidadeDetailComponent } from './vitalidade-detail.component';
import { VitalidadeUpdateComponent } from './vitalidade-update.component';
import { IVitalidade } from 'app/shared/model/vitalidade.model';

@Injectable({ providedIn: 'root' })
export class VitalidadeResolve implements Resolve<IVitalidade> {
  constructor(private service: VitalidadeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVitalidade> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((vitalidade: HttpResponse<Vitalidade>) => vitalidade.body));
    }
    return of(new Vitalidade());
  }
}

export const vitalidadeRoute: Routes = [
  {
    path: '',
    component: VitalidadeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.vitalidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VitalidadeDetailComponent,
    resolve: {
      vitalidade: VitalidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.vitalidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VitalidadeUpdateComponent,
    resolve: {
      vitalidade: VitalidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.vitalidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VitalidadeUpdateComponent,
    resolve: {
      vitalidade: VitalidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.vitalidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
