import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';
import { DoenteDiagnosticoSocialService } from './doente-diagnostico-social.service';

@Component({
  templateUrl: './doente-diagnostico-social-delete-dialog.component.html'
})
export class DoenteDiagnosticoSocialDeleteDialogComponent {
  doenteDiagnosticoSocial: IDoenteDiagnosticoSocial;

  constructor(
    protected doenteDiagnosticoSocialService: DoenteDiagnosticoSocialService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doenteDiagnosticoSocialService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doenteDiagnosticoSocialListModification',
        content: 'Deleted an doenteDiagnosticoSocial'
      });
      this.activeModal.dismiss(true);
    });
  }
}
