import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICentroSaude } from 'app/shared/model/centro-saude.model';
import { CentroSaudeService } from './centro-saude.service';

@Component({
  templateUrl: './centro-saude-delete-dialog.component.html'
})
export class CentroSaudeDeleteDialogComponent {
  centroSaude: ICentroSaude;

  constructor(
    protected centroSaudeService: CentroSaudeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.centroSaudeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'centroSaudeListModification',
        content: 'Deleted an centroSaude'
      });
      this.activeModal.dismiss(true);
    });
  }
}
