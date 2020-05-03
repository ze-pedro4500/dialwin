import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrauConf } from 'app/shared/model/grau-conf.model';
import { GrauConfService } from './grau-conf.service';

@Component({
  templateUrl: './grau-conf-delete-dialog.component.html'
})
export class GrauConfDeleteDialogComponent {
  grauConf: IGrauConf;

  constructor(protected grauConfService: GrauConfService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.grauConfService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'grauConfListModification',
        content: 'Deleted an grauConf'
      });
      this.activeModal.dismiss(true);
    });
  }
}
