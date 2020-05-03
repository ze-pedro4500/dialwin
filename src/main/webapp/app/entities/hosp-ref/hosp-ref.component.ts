import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHospRef } from 'app/shared/model/hosp-ref.model';
import { HospRefService } from './hosp-ref.service';
import { HospRefDeleteDialogComponent } from './hosp-ref-delete-dialog.component';

@Component({
  selector: 'jhi-hosp-ref',
  templateUrl: './hosp-ref.component.html'
})
export class HospRefComponent implements OnInit, OnDestroy {
  hospRefs: IHospRef[];
  eventSubscriber: Subscription;

  constructor(protected hospRefService: HospRefService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.hospRefService.query().subscribe((res: HttpResponse<IHospRef[]>) => {
      this.hospRefs = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInHospRefs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHospRef) {
    return item.id;
  }

  registerChangeInHospRefs() {
    this.eventSubscriber = this.eventManager.subscribe('hospRefListModification', () => this.loadAll());
  }

  delete(hospRef: IHospRef) {
    const modalRef = this.modalService.open(HospRefDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hospRef = hospRef;
  }
}
