import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { DoenteSocioFamiliarComponent } from './doente-socio-familiar.component';
import { DoenteSocioFamiliarDetailComponent } from './doente-socio-familiar-detail.component';
import { DoenteSocioFamiliarUpdateComponent } from './doente-socio-familiar-update.component';
import { DoenteSocioFamiliarDeleteDialogComponent } from './doente-socio-familiar-delete-dialog.component';
import { doenteSocioFamiliarRoute } from './doente-socio-familiar.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(doenteSocioFamiliarRoute)],
  declarations: [
    DoenteSocioFamiliarComponent,
    DoenteSocioFamiliarDetailComponent,
    DoenteSocioFamiliarUpdateComponent,
    DoenteSocioFamiliarDeleteDialogComponent
  ],
  entryComponents: [DoenteSocioFamiliarDeleteDialogComponent]
})
export class DialwinDoenteSocioFamiliarModule {}
