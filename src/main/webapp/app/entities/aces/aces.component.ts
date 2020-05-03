import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IACES } from 'app/shared/model/aces.model';
import { ACESService } from './aces.service';
import { ACESDeleteDialogComponent } from './aces-delete-dialog.component';

@Component({
  selector: 'jhi-aces',
  templateUrl: './aces.component.html'
})
export class ACESComponent implements OnInit, OnDestroy {
  aCES: IACES[];
  eventSubscriber: Subscription;

  constructor(protected aCESService: ACESService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.aCESService.query().subscribe((res: HttpResponse<IACES[]>) => {
      this.aCES = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInACES();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IACES) {
    return item.id;
  }

  registerChangeInACES() {
    this.eventSubscriber = this.eventManager.subscribe('aCESListModification', () => this.loadAll());
  }

  delete(aCES: IACES) {
    const modalRef = this.modalService.open(ACESDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.aCES = aCES;
  }
}
