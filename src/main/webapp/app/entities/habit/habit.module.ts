import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DialwinSharedModule } from 'app/shared/shared.module';
import { HabitComponent } from './habit.component';
import { HabitDetailComponent } from './habit-detail.component';
import { HabitUpdateComponent } from './habit-update.component';
import { HabitDeleteDialogComponent } from './habit-delete-dialog.component';
import { habitRoute } from './habit.route';

@NgModule({
  imports: [DialwinSharedModule, RouterModule.forChild(habitRoute)],
  declarations: [HabitComponent, HabitDetailComponent, HabitUpdateComponent, HabitDeleteDialogComponent],
  entryComponents: [HabitDeleteDialogComponent]
})
export class DialwinHabitModule {}
