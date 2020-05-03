import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { ProfissaoComponent } from './profissao.component';
import { ProfissaoDetailComponent } from './profissao-detail.component';
import { ProfissaoUpdateComponent } from './profissao-update.component';
import { ProfissaoDeleteDialogComponent } from './profissao-delete-dialog.component';
import { profissaoRoute } from './profissao.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(profissaoRoute)],
  declarations: [ProfissaoComponent, ProfissaoDetailComponent, ProfissaoUpdateComponent, ProfissaoDeleteDialogComponent],
  entryComponents: [ProfissaoDeleteDialogComponent]
})
export class DialwinProfissaoModule {}
