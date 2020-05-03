import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';
import { DoenteRegistosIntervencoesService } from './doente-registos-intervencoes.service';

@Component({
  templateUrl: './doente-registos-intervencoes-delete-dialog.component.html'
})
export class DoenteRegistosIntervencoesDeleteDialogComponent {
  doenteRegistosIntervencoes: IDoenteRegistosIntervencoes;

  constructor(
    protected doenteRegistosIntervencoesService: DoenteRegistosIntervencoesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doenteRegistosIntervencoesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doenteRegistosIntervencoesListModification',
        content: 'Deleted an doenteRegistosIntervencoes'
      });
      this.activeModal.dismiss(true);
    });
  }
}
