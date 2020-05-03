import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { DoenteContactosComponent } from './doente-contactos.component';
import { DoenteContactosDetailComponent } from './doente-contactos-detail.component';
import { DoenteContactosUpdateComponent } from './doente-contactos-update.component';
import { DoenteContactosDeleteDialogComponent } from './doente-contactos-delete-dialog.component';
import { doenteContactosRoute } from './doente-contactos.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(doenteContactosRoute)],
  declarations: [
    DoenteContactosComponent,
    DoenteContactosDetailComponent,
    DoenteContactosUpdateComponent,
    DoenteContactosDeleteDialogComponent
  ],
  entryComponents: [DoenteContactosDeleteDialogComponent]
})
export class DialwinDoenteContactosModule {}
