import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IACES } from 'app/shared/model/aces.model';
import { ACESService } from './aces.service';

@Component({
  templateUrl: './aces-delete-dialog.component.html'
})
export class ACESDeleteDialogComponent {
  aCES: IACES;

  constructor(protected aCESService: ACESService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.aCESService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'aCESListModification',
        content: 'Deleted an aCES'
      });
      this.activeModal.dismiss(true);
    });
  }
}
