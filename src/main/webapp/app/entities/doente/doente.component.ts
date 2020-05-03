import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from './doente.service';
import { DoenteDeleteDialogComponent } from './doente-delete-dialog.component';

@Component({
  selector: 'jhi-doente',
  templateUrl: './doente.component.html'
})
export class DoenteComponent implements OnInit, OnDestroy {
  doentes: IDoente[];
  eventSubscriber: Subscription;

  constructor(protected doenteService: DoenteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.doenteService.query().subscribe((res: HttpResponse<IDoente[]>) => {
      this.doentes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDoentes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDoente) {
    return item.id;
  }

  registerChangeInDoentes() {
    this.eventSubscriber = this.eventManager.subscribe('doenteListModification', () => this.loadAll());
  }

  delete(doente: IDoente) {
    const modalRef = this.modalService.open(DoenteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.doente = doente;
  }
}
