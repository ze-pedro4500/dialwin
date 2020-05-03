import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { HospRefComponent } from './hosp-ref.component';
import { HospRefDetailComponent } from './hosp-ref-detail.component';
import { HospRefUpdateComponent } from './hosp-ref-update.component';
import { HospRefDeleteDialogComponent } from './hosp-ref-delete-dialog.component';
import { hospRefRoute } from './hosp-ref.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(hospRefRoute)],
  declarations: [HospRefComponent, HospRefDetailComponent, HospRefUpdateComponent, HospRefDeleteDialogComponent],
  entryComponents: [HospRefDeleteDialogComponent]
})
export class DialwinHospRefModule {}
