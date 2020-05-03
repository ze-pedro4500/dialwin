import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';
import { SubSisGrupoService } from './sub-sis-grupo.service';

@Component({
  templateUrl: './sub-sis-grupo-delete-dialog.component.html'
})
export class SubSisGrupoDeleteDialogComponent {
  subSisGrupo: ISubSisGrupo;

  constructor(
    protected subSisGrupoService: SubSisGrupoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.subSisGrupoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'subSisGrupoListModification',
        content: 'Deleted an subSisGrupo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
