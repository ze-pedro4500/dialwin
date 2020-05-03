import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GrauConf } from 'app/shared/model/grau-conf.model';
import { GrauConfService } from './grau-conf.service';
import { GrauConfComponent } from './grau-conf.component';
import { GrauConfDetailComponent } from './grau-conf-detail.component';
import { GrauConfUpdateComponent } from './grau-conf-update.component';
import { IGrauConf } from 'app/shared/model/grau-conf.model';

@Injectable({ providedIn: 'root' })
export class GrauConfResolve implements Resolve<IGrauConf> {
  constructor(private service: GrauConfService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrauConf> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((grauConf: HttpResponse<GrauConf>) => grauConf.body));
    }
    return of(new GrauConf());
  }
}

export const grauConfRoute: Routes = [
  {
    path: '',
    component: GrauConfComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.grauConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GrauConfDetailComponent,
    resolve: {
      grauConf: GrauConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.grauConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GrauConfUpdateComponent,
    resolve: {
      grauConf: GrauConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.grauConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GrauConfUpdateComponent,
    resolve: {
      grauConf: GrauConfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.grauConf.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
