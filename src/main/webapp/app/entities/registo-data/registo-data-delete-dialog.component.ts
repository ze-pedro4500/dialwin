import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegistoData } from 'app/shared/model/registo-data.model';
import { RegistoDataService } from './registo-data.service';

@Component({
  templateUrl: './registo-data-delete-dialog.component.html'
})
export class RegistoDataDeleteDialogComponent {
  registoData?: IRegistoData;

  constructor(
    protected registoDataService: RegistoDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.registoDataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('registoDataListModification');
      this.activeModal.close();
    });
  }
}
