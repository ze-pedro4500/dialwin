import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHabit } from 'app/shared/model/habit.model';
import { HabitService } from './habit.service';

@Component({
  templateUrl: './habit-delete-dialog.component.html'
})
export class HabitDeleteDialogComponent {
  habit: IHabit;

  constructor(protected habitService: HabitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.habitService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'habitListModification',
        content: 'Deleted an habit'
      });
      this.activeModal.dismiss(true);
    });
  }
}
