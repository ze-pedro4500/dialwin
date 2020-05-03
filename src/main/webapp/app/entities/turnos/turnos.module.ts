import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { TurnosComponent } from './turnos.component';
import { TurnosDetailComponent } from './turnos-detail.component';
import { TurnosUpdateComponent } from './turnos-update.component';
import { TurnosDeleteDialogComponent } from './turnos-delete-dialog.component';
import { turnosRoute } from './turnos.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(turnosRoute)],
  declarations: [TurnosComponent, TurnosDetailComponent, TurnosUpdateComponent, TurnosDeleteDialogComponent],
  entryComponents: [TurnosDeleteDialogComponent]
})
export class DialwinTurnosModule {}
