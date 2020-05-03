import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { IDoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DoenteContactosOutrosService } from '../doente-contactos-outros/doente-contactos-outros.service'
import { HttpResponse } from '@angular/common/http';
import { DoenteContactosOutrosDeleteDialogComponent } from '../doente-contactos-outros/doente-contactos-outros-delete-dialog.component';
import { DataService } from '../../data.service'


@Component({
  selector: 'jhi-contactosoutros',
  templateUrl: './contactosoutros.component.html',
  styleUrls: ['./contactosoutros.component.scss']
})
export class ContactosoutrosComponent implements OnInit {

  doenteContactosOutros: IDoenteContactosOutros[];
  eventSubscriber: Subscription;
  doenteId:number;
  
  constructor(
    protected doenteContactosOutrosService: DoenteContactosOutrosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private data: DataService
  ) { }

    


  loadAll() {
    this.data.currentDoente.subscribe((doenteId) => {
      this.doenteId=doenteId
      this.doenteContactosOutrosService.search(this.doenteId).subscribe((res: HttpResponse<IDoenteContactosOutros[]>) => {
        this.doenteContactosOutros = res.body;
    })
  });
}

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDoenteContactosOutros();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDoenteContactosOutros) {
    return item.id;
  }

  registerChangeInDoenteContactosOutros() {
    this.eventSubscriber = this.eventManager.subscribe('doenteContactosOutrosListModification', () => this.loadAll());
  }

  delete(doenteContactosOutros: IDoenteContactosOutros) {
    const modalRef = this.modalService.open(DoenteContactosOutrosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.doenteContactosOutros = doenteContactosOutros;
  }
}
