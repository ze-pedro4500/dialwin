import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegistoData } from 'app/shared/model/registo-data.model';
import { RegistoDataService } from './registo-data.service';
import { RegistoDataDeleteDialogComponent } from './registo-data-delete-dialog.component';

@Component({
  selector: 'jhi-registo-data',
  templateUrl: './registo-data.component.html'
})
export class RegistoDataComponent implements OnInit, OnDestroy {
  registoData?: IRegistoData[];
  eventSubscriber?: Subscription;

  constructor(
    protected registoDataService: RegistoDataService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.registoDataService.query().subscribe((res: HttpResponse<IRegistoData[]>) => (this.registoData = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRegistoData();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRegistoData): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRegistoData(): void {
    this.eventSubscriber = this.eventManager.subscribe('registoDataListModification', () => this.loadAll());
  }

  delete(registoData: IRegistoData): void {
    const modalRef = this.modalService.open(RegistoDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.registoData = registoData;
  }
}
