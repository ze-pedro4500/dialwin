import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICentroSaude } from 'app/shared/model/centro-saude.model';
import { CentroSaudeService } from './centro-saude.service';
import { CentroSaudeDeleteDialogComponent } from './centro-saude-delete-dialog.component';

@Component({
  selector: 'jhi-centro-saude',
  templateUrl: './centro-saude.component.html'
})
export class CentroSaudeComponent implements OnInit, OnDestroy {
  centroSaudes: ICentroSaude[];
  eventSubscriber: Subscription;

  constructor(
    protected centroSaudeService: CentroSaudeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.centroSaudeService.query().subscribe((res: HttpResponse<ICentroSaude[]>) => {
      this.centroSaudes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCentroSaudes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICentroSaude) {
    return item.id;
  }

  registerChangeInCentroSaudes() {
    this.eventSubscriber = this.eventManager.subscribe('centroSaudeListModification', () => this.loadAll());
  }

  delete(centroSaude: ICentroSaude) {
    const modalRef = this.modalService.open(CentroSaudeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.centroSaude = centroSaude;
  }
}
