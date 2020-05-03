import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { DoenteRegistosIntervencoesComponent } from './doente-registos-intervencoes.component';
import { DoenteRegistosIntervencoesDetailComponent } from './doente-registos-intervencoes-detail.component';
import { DoenteRegistosIntervencoesUpdateComponent } from './doente-registos-intervencoes-update.component';
import { DoenteRegistosIntervencoesDeleteDialogComponent } from './doente-registos-intervencoes-delete-dialog.component';
import { doenteRegistosIntervencoesRoute } from './doente-registos-intervencoes.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(doenteRegistosIntervencoesRoute)],
  declarations: [
    DoenteRegistosIntervencoesComponent,
    DoenteRegistosIntervencoesDetailComponent,
    DoenteRegistosIntervencoesUpdateComponent,
    DoenteRegistosIntervencoesDeleteDialogComponent
  ],
  entryComponents: [DoenteRegistosIntervencoesDeleteDialogComponent]
})
export class DialwinDoenteRegistosIntervencoesModule {}
