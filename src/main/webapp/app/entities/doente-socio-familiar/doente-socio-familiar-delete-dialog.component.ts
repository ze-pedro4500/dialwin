import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';
import { DoenteSocioFamiliarService } from './doente-socio-familiar.service';

@Component({
  templateUrl: './doente-socio-familiar-delete-dialog.component.html'
})
export class DoenteSocioFamiliarDeleteDialogComponent {
  doenteSocioFamiliar: IDoenteSocioFamiliar;

  constructor(
    protected doenteSocioFamiliarService: DoenteSocioFamiliarService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doenteSocioFamiliarService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doenteSocioFamiliarListModification',
        content: 'Deleted an doenteSocioFamiliar'
      });
      this.activeModal.dismiss(true);
    });
  }
}
