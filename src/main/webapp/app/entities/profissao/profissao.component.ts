import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfissao } from 'app/shared/model/profissao.model';
import { ProfissaoService } from './profissao.service';
import { ProfissaoDeleteDialogComponent } from './profissao-delete-dialog.component';

@Component({
  selector: 'jhi-profissao',
  templateUrl: './profissao.component.html'
})
export class ProfissaoComponent implements OnInit, OnDestroy {
  profissaos: IProfissao[];
  eventSubscriber: Subscription;

  constructor(protected profissaoService: ProfissaoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.profissaoService.query().subscribe((res: HttpResponse<IProfissao[]>) => {
      this.profissaos = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProfissaos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProfissao) {
    return item.id;
  }

  registerChangeInProfissaos() {
    this.eventSubscriber = this.eventManager.subscribe('profissaoListModification', () => this.loadAll());
  }

  delete(profissao: IProfissao) {
    const modalRef = this.modalService.open(ProfissaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.profissao = profissao;
  }
}
