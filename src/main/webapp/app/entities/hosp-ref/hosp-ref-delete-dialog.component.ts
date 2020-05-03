import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHospRef } from 'app/shared/model/hosp-ref.model';
import { HospRefService } from './hosp-ref.service';

@Component({
  templateUrl: './hosp-ref-delete-dialog.component.html'
})
export class HospRefDeleteDialogComponent {
  hospRef: IHospRef;

  constructor(protected hospRefService: HospRefService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.hospRefService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'hospRefListModification',
        content: 'Deleted an hospRef'
      });
      this.activeModal.dismiss(true);
    });
  }
}
