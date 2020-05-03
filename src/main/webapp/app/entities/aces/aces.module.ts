import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { ACESComponent } from './aces.component';
import { ACESDetailComponent } from './aces-detail.component';
import { ACESUpdateComponent } from './aces-update.component';
import { ACESDeleteDialogComponent } from './aces-delete-dialog.component';
import { aCESRoute } from './aces.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(aCESRoute)],
  declarations: [ACESComponent, ACESDetailComponent, ACESUpdateComponent, ACESDeleteDialogComponent],
  entryComponents: [ACESDeleteDialogComponent]
})
export class DialwinACESModule {}
