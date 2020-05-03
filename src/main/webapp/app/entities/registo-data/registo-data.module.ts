import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { RegistoDataComponent } from './registo-data.component';
import { RegistoDataDetailComponent } from './registo-data-detail.component';
import { RegistoDataUpdateComponent } from './registo-data-update.component';
import { RegistoDataDeleteDialogComponent } from './registo-data-delete-dialog.component';
import { registoDataRoute } from './registo-data.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(registoDataRoute)],
  declarations: [RegistoDataComponent, RegistoDataDetailComponent, RegistoDataUpdateComponent, RegistoDataDeleteDialogComponent],
  entryComponents: [RegistoDataDeleteDialogComponent]
})
export class DialwinRegistoDataModule {}
