import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';
import { DoenteIdentidadeService } from './doente-identidade.service';

@Component({
  templateUrl: './doente-identidade-delete-dialog.component.html'
})
export class DoenteIdentidadeDeleteDialogComponent {
  doenteIdentidade: IDoenteIdentidade;

  constructor(
    protected doenteIdentidadeService: DoenteIdentidadeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doenteIdentidadeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doenteIdentidadeListModification',
        content: 'Deleted an doenteIdentidade'
      });
      this.activeModal.dismiss(true);
    });
  }
}
