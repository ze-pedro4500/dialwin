import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';
import { DoenteContactosOutrosService } from './doente-contactos-outros.service';

@Component({
  templateUrl: './doente-contactos-outros-delete-dialog.component.html'
})
export class DoenteContactosOutrosDeleteDialogComponent {
  doenteContactosOutros: IDoenteContactosOutros;

  constructor(
    protected doenteContactosOutrosService: DoenteContactosOutrosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doenteContactosOutrosService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doenteContactosOutrosListModification',
        content: 'Deleted an doenteContactosOutros'
      });
      this.activeModal.dismiss(true);
    });
  }
}
