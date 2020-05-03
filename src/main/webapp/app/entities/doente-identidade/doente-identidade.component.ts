import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';
import { DoenteIdentidadeService } from './doente-identidade.service';
import { DoenteIdentidadeDeleteDialogComponent } from './doente-identidade-delete-dialog.component';

@Component({
  selector: 'jhi-doente-identidade',
  templateUrl: './doente-identidade.component.html'
})
export class DoenteIdentidadeComponent implements OnInit, OnDestroy {
  doenteIdentidades: IDoenteIdentidade[];
  eventSubscriber: Subscription;

  constructor(
    protected doenteIdentidadeService: DoenteIdentidadeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.doenteIdentidadeService.query().subscribe((res: HttpResponse<IDoenteIdentidade[]>) => {
      this.doenteIdentidades = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDoenteIdentidades();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDoenteIdentidade) {
    return item.id;
  }

  registerChangeInDoenteIdentidades() {
    this.eventSubscriber = this.eventManager.subscribe('doenteIdentidadeListModification', () => this.loadAll());
  }

  delete(doenteIdentidade: IDoenteIdentidade) {
    const modalRef = this.modalService.open(DoenteIdentidadeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.doenteIdentidade = doenteIdentidade;
  }
}
