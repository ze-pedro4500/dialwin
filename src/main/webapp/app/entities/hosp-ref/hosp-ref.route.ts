import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HospRef } from 'app/shared/model/hosp-ref.model';
import { HospRefService } from './hosp-ref.service';
import { HospRefComponent } from './hosp-ref.component';
import { HospRefDetailComponent } from './hosp-ref-detail.component';
import { HospRefUpdateComponent } from './hosp-ref-update.component';
import { IHospRef } from 'app/shared/model/hosp-ref.model';

@Injectable({ providedIn: 'root' })
export class HospRefResolve implements Resolve<IHospRef> {
  constructor(private service: HospRefService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHospRef> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((hospRef: HttpResponse<HospRef>) => hospRef.body));
    }
    return of(new HospRef());
  }
}

export const hospRefRoute: Routes = [
  {
    path: '',
    component: HospRefComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.hospRef.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HospRefDetailComponent,
    resolve: {
      hospRef: HospRefResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.hospRef.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HospRefUpdateComponent,
    resolve: {
      hospRef: HospRefResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.hospRef.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HospRefUpdateComponent,
    resolve: {
      hospRef: HospRefResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.hospRef.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
