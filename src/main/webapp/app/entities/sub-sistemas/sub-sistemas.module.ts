import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { SubSistemasComponent } from './sub-sistemas.component';
import { SubSistemasDetailComponent } from './sub-sistemas-detail.component';
import { SubSistemasUpdateComponent } from './sub-sistemas-update.component';
import { SubSistemasDeleteDialogComponent } from './sub-sistemas-delete-dialog.component';
import { subSistemasRoute } from './sub-sistemas.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(subSistemasRoute)],
  declarations: [SubSistemasComponent, SubSistemasDetailComponent, SubSistemasUpdateComponent, SubSistemasDeleteDialogComponent],
  entryComponents: [SubSistemasDeleteDialogComponent]
})
export class DialwinSubSistemasModule {}
