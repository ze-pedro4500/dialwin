import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { DoenteHistMovimentosComponent } from './doente-hist-movimentos.component';
import { DoenteHistMovimentosDetailComponent } from './doente-hist-movimentos-detail.component';
import { DoenteHistMovimentosUpdateComponent } from './doente-hist-movimentos-update.component';
import { DoenteHistMovimentosDeleteDialogComponent } from './doente-hist-movimentos-delete-dialog.component';
import { doenteHistMovimentosRoute } from './doente-hist-movimentos.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(doenteHistMovimentosRoute)],
  declarations: [
    DoenteHistMovimentosComponent,
    DoenteHistMovimentosDetailComponent,
    DoenteHistMovimentosUpdateComponent,
    DoenteHistMovimentosDeleteDialogComponent
  ],
  entryComponents: [DoenteHistMovimentosDeleteDialogComponent]
})
export class DialwinDoenteHistMovimentosModule {}
