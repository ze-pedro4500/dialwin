import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { DoenteComponent } from './doente.component';
import { DoenteDetailComponent } from './doente-detail.component';
import { DoenteUpdateComponent } from './doente-update.component';
import { DoenteDeleteDialogComponent } from './doente-delete-dialog.component';
import { doenteRoute } from './doente.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(doenteRoute)],
  declarations: [DoenteComponent, DoenteDetailComponent, DoenteUpdateComponent, DoenteDeleteDialogComponent],
  entryComponents: [DoenteDeleteDialogComponent]
})
export class DialwinDoenteModule {}
