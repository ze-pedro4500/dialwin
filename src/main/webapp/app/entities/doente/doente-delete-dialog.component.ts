import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from './doente.service';

@Component({
  templateUrl: './doente-delete-dialog.component.html'
})
export class DoenteDeleteDialogComponent {
  doente: IDoente;

  constructor(protected doenteService: DoenteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doenteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doenteListModification',
        content: 'Deleted an doente'
      });
      this.activeModal.dismiss(true);
    });
  }
}
