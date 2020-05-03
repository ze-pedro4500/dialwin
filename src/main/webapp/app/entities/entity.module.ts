import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { pathToFileURL } from 'url';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vitalidade',
        loadChildren: () => import('./vitalidade/vitalidade.module').then(m => m.DialwinVitalidadeModule)
      },
      {
        path: 'aces',
        loadChildren: () => import('./aces/aces.module').then(m => m.DialwinACESModule)
      },
      {
        path: 'doente',
        loadChildren: () => import('./doente/doente.module').then(m => m.DialwinDoenteModule)
      },
      {
        path: 'doente-identidade',
        loadChildren: () => import('./doente-identidade/doente-identidade.module').then(m => m.DialwinDoenteIdentidadeModule)
      },
      {
        path: 'pais',
        loadChildren: () => import('./pais/pais.module').then(m => m.DialwinPaisModule)
      },
      {
        path: 'doente-contactos',
        loadChildren: () => import('./doente-contactos/doente-contactos.module').then(m => m.DialwinDoenteContactosModule)
      },
      {
        path: 'doente-contactos-outros',
        loadChildren: () =>
          import('./doente-contactos-outros/doente-contactos-outros.module').then(m => m.DialwinDoenteContactosOutrosModule)
      },
      {
        path: 'sit-prof',
        loadChildren: () => import('./sit-prof/sit-prof.module').then(m => m.DialwinSitProfModule)
      },
      {
        path: 'profissao',
        loadChildren: () => import('./profissao/profissao.module').then(m => m.DialwinProfissaoModule)
      },
      {
        path: 'doente-socio-familiar',
        loadChildren: () => import('./doente-socio-familiar/doente-socio-familiar.module').then(m => m.DialwinDoenteSocioFamiliarModule)
      },
      {
        path: 'doente-diagnostico-social',
        loadChildren: () =>
          import('./doente-diagnostico-social/doente-diagnostico-social.module').then(m => m.DialwinDoenteDiagnosticoSocialModule)
      },
      {
        path: 'doente-registos-intervencoes',
        loadChildren: () =>
          import('./doente-registos-intervencoes/doente-registos-intervencoes.module').then(m => m.DialwinDoenteRegistosIntervencoesModule)
      },
      {
        path: 'doente-hist-movimentos',
        loadChildren: () => import('./doente-hist-movimentos/doente-hist-movimentos.module').then(m => m.DialwinDoenteHistMovimentosModule)
      },
      {
        path: 'horario-doente',
        loadChildren: () => import('./horario-doente/horario-doente.module').then(m => m.DialwinHorarioDoenteModule)
      },
      {
        path: 'sub-sistemas',
        loadChildren: () => import('./sub-sistemas/sub-sistemas.module').then(m => m.DialwinSubSistemasModule)
      },
      {
        path: 'sub-sis-grupo',
        loadChildren: () => import('./sub-sis-grupo/sub-sis-grupo.module').then(m => m.DialwinSubSisGrupoModule)
      },
      {
        path: 'turnos',
        loadChildren: () => import('./turnos/turnos.module').then(m => m.DialwinTurnosModule)
      },
      {
        path: 'centro-saude',
        loadChildren: () => import('./centro-saude/centro-saude.module').then(m => m.DialwinCentroSaudeModule)
      },
      {
        path: 'hosp-ref',
        loadChildren: () => import('./hosp-ref/hosp-ref.module').then(m => m.DialwinHospRefModule)
      },
      {
        path: 'habit',
        loadChildren: () => import('./habit/habit.module').then(m => m.DialwinHabitModule)
      },
      {
        path: 'grau-conf',
        loadChildren: () => import('./grau-conf/grau-conf.module').then(m => m.DialwinGrauConfModule)
      },
      {
        path: 'user-extra',
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.DialwinUserExtraModule)
      },
      {
        path: 'user-profile',
        loadChildren: () => import('./user-profile/user-profile.module').then(m => m.DialwinUserProfileModule)
      },
      {
        path: 'user-permissions',
        loadChildren: () => import('./user-permissions/user-permissions.module').then(m => m.DialwinUserPermissionsModule)
      },
      {
        path: 'demografia',
        loadChildren: () => import('./demografia/demografia.module').then(m => m.DialwinDemografiaModule)
      },
      {
        path: 'identidade',
        loadChildren: () => import('../entities/demoid/demoid.component').then(m => m.DemoidComponent)
      },
      {
        path: 'registo-data',
        loadChildren: () => import('./registo-data/registo-data.module').then(m => m.DialwinRegistoDataModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class DialwinEntityModule {}
