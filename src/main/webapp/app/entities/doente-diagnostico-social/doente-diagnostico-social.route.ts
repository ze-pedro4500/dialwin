import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';
import { DoenteDiagnosticoSocialService } from './doente-diagnostico-social.service';
import { DoenteDiagnosticoSocialComponent } from './doente-diagnostico-social.component';
import { DoenteDiagnosticoSocialDetailComponent } from './doente-diagnostico-social-detail.component';
import { DoenteDiagnosticoSocialUpdateComponent } from './doente-diagnostico-social-update.component';
import { IDoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';

@Injectable({ providedIn: 'root' })
export class DoenteDiagnosticoSocialResolve implements Resolve<IDoenteDiagnosticoSocial> {
  constructor(private service: DoenteDiagnosticoSocialService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoenteDiagnosticoSocial> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((doenteDiagnosticoSocial: HttpResponse<DoenteDiagnosticoSocial>) => doenteDiagnosticoSocial.body));
    }
    return of(new DoenteDiagnosticoSocial());
  }
}

export const doenteDiagnosticoSocialRoute: Routes = [
  {
    path: '',
    component: DoenteDiagnosticoSocialComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteDiagnosticoSocial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoenteDiagnosticoSocialDetailComponent,
    resolve: {
      doenteDiagnosticoSocial: DoenteDiagnosticoSocialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteDiagnosticoSocial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoenteDiagnosticoSocialUpdateComponent,
    resolve: {
      doenteDiagnosticoSocial: DoenteDiagnosticoSocialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteDiagnosticoSocial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoenteDiagnosticoSocialUpdateComponent,
    resolve: {
      doenteDiagnosticoSocial: DoenteDiagnosticoSocialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.doenteDiagnosticoSocial.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
