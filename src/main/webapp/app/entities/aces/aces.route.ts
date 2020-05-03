import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ACES } from 'app/shared/model/aces.model';
import { ACESService } from './aces.service';
import { ACESComponent } from './aces.component';
import { ACESDetailComponent } from './aces-detail.component';
import { ACESUpdateComponent } from './aces-update.component';
import { IACES } from 'app/shared/model/aces.model';

@Injectable({ providedIn: 'root' })
export class ACESResolve implements Resolve<IACES> {
  constructor(private service: ACESService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IACES> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((aCES: HttpResponse<ACES>) => aCES.body));
    }
    return of(new ACES());
  }
}

export const aCESRoute: Routes = [
  {
    path: '',
    component: ACESComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.aCES.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ACESDetailComponent,
    resolve: {
      aCES: ACESResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.aCES.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ACESUpdateComponent,
    resolve: {
      aCES: ACESResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.aCES.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ACESUpdateComponent,
    resolve: {
      aCES: ACESResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.aCES.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
