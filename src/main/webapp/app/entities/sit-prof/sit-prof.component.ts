import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISitProf } from 'app/shared/model/sit-prof.model';
import { SitProfService } from './sit-prof.service';
import { SitProfDeleteDialogComponent } from './sit-prof-delete-dialog.component';

@Component({
  selector: 'jhi-sit-prof',
  templateUrl: './sit-prof.component.html'
})
export class SitProfComponent implements OnInit, OnDestroy {
  sitProfs: ISitProf[];
  eventSubscriber: Subscription;

  constructor(protected sitProfService: SitProfService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.sitProfService.query().subscribe((res: HttpResponse<ISitProf[]>) => {
      this.sitProfs = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSitProfs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISitProf) {
    return item.id;
  }

  registerChangeInSitProfs() {
    this.eventSubscriber = this.eventManager.subscribe('sitProfListModification', () => this.loadAll());
  }

  delete(sitProf: ISitProf) {
    const modalRef = this.modalService.open(SitProfDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sitProf = sitProf;
  }
}
