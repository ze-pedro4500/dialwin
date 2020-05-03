import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRegistoData, RegistoData } from 'app/shared/model/registo-data.model';
import { RegistoDataService } from './registo-data.service';
import { RegistoDataComponent } from './registo-data.component';
import { RegistoDataDetailComponent } from './registo-data-detail.component';
import { RegistoDataUpdateComponent } from './registo-data-update.component';

@Injectable({ providedIn: 'root' })
export class RegistoDataResolve implements Resolve<IRegistoData> {
  constructor(private service: RegistoDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegistoData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((registoData: HttpResponse<RegistoData>) => {
          if (registoData.body) {
            return of(registoData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RegistoData());
  }
}

export const registoDataRoute: Routes = [
  {
    path: '',
    component: RegistoDataComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.registoData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegistoDataDetailComponent,
    resolve: {
      registoData: RegistoDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.registoData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegistoDataUpdateComponent,
    resolve: {
      registoData: RegistoDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.registoData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegistoDataUpdateComponent,
    resolve: {
      registoData: RegistoDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.registoData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
