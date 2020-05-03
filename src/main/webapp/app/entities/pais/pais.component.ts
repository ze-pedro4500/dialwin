import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';
import { PaisDeleteDialogComponent } from './pais-delete-dialog.component';

@Component({
  selector: 'jhi-pais',
  templateUrl: './pais.component.html'
})
export class PaisComponent implements OnInit, OnDestroy {
  pais: IPais[];
  eventSubscriber: Subscription;

  constructor(protected paisService: PaisService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.paisService.query().subscribe((res: HttpResponse<IPais[]>) => {
      this.pais = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPais();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPais) {
    return item.id;
  }

  registerChangeInPais() {
    this.eventSubscriber = this.eventManager.subscribe('paisListModification', () => this.loadAll());
  }

  delete(pais: IPais) {
    const modalRef = this.modalService.open(PaisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pais = pais;
  }
}
