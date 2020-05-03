import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Profissao } from 'app/shared/model/profissao.model';
import { ProfissaoService } from './profissao.service';
import { ProfissaoComponent } from './profissao.component';
import { ProfissaoDetailComponent } from './profissao-detail.component';
import { ProfissaoUpdateComponent } from './profissao-update.component';
import { IProfissao } from 'app/shared/model/profissao.model';

@Injectable({ providedIn: 'root' })
export class ProfissaoResolve implements Resolve<IProfissao> {
  constructor(private service: ProfissaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfissao> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((profissao: HttpResponse<Profissao>) => profissao.body));
    }
    return of(new Profissao());
  }
}

export const profissaoRoute: Routes = [
  {
    path: '',
    component: ProfissaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.profissao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfissaoDetailComponent,
    resolve: {
      profissao: ProfissaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.profissao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfissaoUpdateComponent,
    resolve: {
      profissao: ProfissaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.profissao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfissaoUpdateComponent,
    resolve: {
      profissao: ProfissaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dialwinApp.profissao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
