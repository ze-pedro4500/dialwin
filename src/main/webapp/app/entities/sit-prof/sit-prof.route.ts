import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SitProf } from 'app/shared/model/sit-prof.model';
import { SitProfService } from './sit-prof.service';
import { SitProfComponent } from './sit-prof.component';
import { SitProfDetailComponent } from './sit-prof-detail.component';
import { SitProfUpdateComponent } from './sit-prof-update.component';
import { ISitProf } from 'app/shared/model/sit-prof.model';

@Injectable({ providedIn: 'root' })
export class SitProfResolve implements Resolve<ISitProf> {
  constructor(private service: SitProfService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISitProf> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((sitProf: HttpResponse<SitProf>) => sitProf.body));
    }
    return of(new SitProf());
  }
}

export const sitProfRoute: Routes = [
  {
    path: '',
    component: SitProfComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.sitProf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SitProfDetailComponent,
    resolve: {
      sitProf: SitProfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.sitProf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SitProfUpdateComponent,
    resolve: {
      sitProf: SitProfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.sitProf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SitProfUpdateComponent,
    resolve: {
      sitProf: SitProfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.sitProf.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
