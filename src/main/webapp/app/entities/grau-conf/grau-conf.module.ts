import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { GrauConfComponent } from './grau-conf.component';
import { GrauConfDetailComponent } from './grau-conf-detail.component';
import { GrauConfUpdateComponent } from './grau-conf-update.component';
import { GrauConfDeleteDialogComponent } from './grau-conf-delete-dialog.component';
import { grauConfRoute } from './grau-conf.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(grauConfRoute)],
  declarations: [GrauConfComponent, GrauConfDetailComponent, GrauConfUpdateComponent, GrauConfDeleteDialogComponent],
  entryComponents: [GrauConfDeleteDialogComponent]
})
export class DialwinGrauConfModule {}
