import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { SitProfComponent } from './sit-prof.component';
import { SitProfDetailComponent } from './sit-prof-detail.component';
import { SitProfUpdateComponent } from './sit-prof-update.component';
import { SitProfDeleteDialogComponent } from './sit-prof-delete-dialog.component';
import { sitProfRoute } from './sit-prof.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(sitProfRoute)],
  declarations: [SitProfComponent, SitProfDetailComponent, SitProfUpdateComponent, SitProfDeleteDialogComponent],
  entryComponents: [SitProfDeleteDialogComponent]
})
export class DialwinSitProfModule {}
