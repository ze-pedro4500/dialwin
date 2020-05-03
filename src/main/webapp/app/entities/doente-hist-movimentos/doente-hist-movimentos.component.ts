import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';
import { DoenteHistMovimentosService } from './doente-hist-movimentos.service';
import { DoenteHistMovimentosDeleteDialogComponent } from './doente-hist-movimentos-delete-dialog.component';

@Component({
  selector: 'jhi-doente-hist-movimentos',
  templateUrl: './doente-hist-movimentos.component.html'
})
export class DoenteHistMovimentosComponent implements OnInit, OnDestroy {
  doenteHistMovimentos: IDoenteHistMovimentos[];
  eventSubscriber: Subscription;

  constructor(
    protected doenteHistMovimentosService: DoenteHistMovimentosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.doenteHistMovimentosService.query().subscribe((res: HttpResponse<IDoenteHistMovimentos[]>) => {
      this.doenteHistMovimentos = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDoenteHistMovimentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDoenteHistMovimentos) {
    return item.id;
  }

  registerChangeInDoenteHistMovimentos() {
    this.eventSubscriber = this.eventManager.subscribe('doenteHistMovimentosListModification', () => this.loadAll());
  }

  delete(doenteHistMovimentos: IDoenteHistMovimentos) {
    const modalRef = this.modalService.open(DoenteHistMovimentosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.doenteHistMovimentos = doenteHistMovimentos;
  }
}
