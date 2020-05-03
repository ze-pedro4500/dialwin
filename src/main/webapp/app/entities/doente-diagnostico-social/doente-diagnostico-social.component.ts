import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';
import { DoenteDiagnosticoSocialService } from './doente-diagnostico-social.service';
import { DoenteDiagnosticoSocialDeleteDialogComponent } from './doente-diagnostico-social-delete-dialog.component';

@Component({
  selector: 'jhi-doente-diagnostico-social',
  templateUrl: './doente-diagnostico-social.component.html'
})
export class DoenteDiagnosticoSocialComponent implements OnInit, OnDestroy {
  doenteDiagnosticoSocials: IDoenteDiagnosticoSocial[];
  eventSubscriber: Subscription;

  constructor(
    protected doenteDiagnosticoSocialService: DoenteDiagnosticoSocialService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.doenteDiagnosticoSocialService.query().subscribe((res: HttpResponse<IDoenteDiagnosticoSocial[]>) => {
      this.doenteDiagnosticoSocials = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDoenteDiagnosticoSocials();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDoenteDiagnosticoSocial) {
    return item.id;
  }

  registerChangeInDoenteDiagnosticoSocials() {
    this.eventSubscriber = this.eventManager.subscribe('doenteDiagnosticoSocialListModification', () => this.loadAll());
  }

  delete(doenteDiagnosticoSocial: IDoenteDiagnosticoSocial) {
    const modalRef = this.modalService.open(DoenteDiagnosticoSocialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.doenteDiagnosticoSocial = doenteDiagnosticoSocial;
  }
}
