import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CentroSaude } from 'app/shared/model/centro-saude.model';
import { CentroSaudeService } from './centro-saude.service';
import { CentroSaudeComponent } from './centro-saude.component';
import { CentroSaudeDetailComponent } from './centro-saude-detail.component';
import { CentroSaudeUpdateComponent } from './centro-saude-update.component';
import { ICentroSaude } from 'app/shared/model/centro-saude.model';

@Injectable({ providedIn: 'root' })
export class CentroSaudeResolve implements Resolve<ICentroSaude> {
  constructor(private service: CentroSaudeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICentroSaude> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((centroSaude: HttpResponse<CentroSaude>) => centroSaude.body));
    }
    return of(new CentroSaude());
  }
}

export const centroSaudeRoute: Routes = [
  {
    path: '',
    component: CentroSaudeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.centroSaude.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CentroSaudeDetailComponent,
    resolve: {
      centroSaude: CentroSaudeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.centroSaude.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CentroSaudeUpdateComponent,
    resolve: {
      centroSaude: CentroSaudeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.centroSaude.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CentroSaudeUpdateComponent,
    resolve: {
      centroSaude: CentroSaudeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.centroSaude.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
