import { Component, OnInit, OnDestroy } from '@angular/core';
import { DataService } from '../../data.service'
import { DoenteHistMovimentosService } from '../doente-hist-movimentos/doente-hist-movimentos.service';
import { Subscription } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { DoenteHistMovimentosDeleteDialogComponent } from '../doente-hist-movimentos/doente-hist-movimentos-delete-dialog.component';
import { IDoente } from 'app/shared/model/doente.model';
import { FormBuilder, Validators } from '@angular/forms';
import { DoenteService } from 'app/entities/doente/doente.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { IDoenteHistMovimentos, DoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';
import { JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs';



@Component({
  selector: 'jhi-movimentos',
  templateUrl: './movimentos.component.html',
  styleUrls: ['./movimentos.component.scss']
})
export class MovimentosComponent implements OnInit, OnDestroy {
  doenteId:number;
  doenteHistMovimentos: IDoenteHistMovimentos[];
  eventSubscriber: Subscription;
  historico:boolean;
  newMovimento=false;

  isSaving:boolean;
  doente:IDoente;
  dataDp:any;
  doentes:IDoente[];


  editForm = this.fb.group({
    id: [],
    data: [],
    situacao: [],
    obs: [],
    doente: []
  });


  constructor(
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected doenteService: DoenteService,
    protected doenteHistMovimentosService: DoenteHistMovimentosService,
    private fb: FormBuilder,
    private data: DataService
    ) { }


    print(){
      window.print();
    }

    save() {
      this.isSaving = true;
      const doenteHistMovimentos = this.createFromForm();
        this.subscribeToSaveResponse(this.doenteHistMovimentosService.create(doenteHistMovimentos));
        this.newMovimento=false;
        this.loadAll();
        this.loadAll();
        this.delay(2000);
        this.loadAll();
        this.delay(2000);
        this.loadAll();
        this.loadAll();
        this.loadAll();

    }

    cancel(){
      this.newMovimento=false;
      this.editForm.reset();
    }

    async delay(ms: number) {
      await new Promise(resolve => setTimeout(()=>resolve(), ms)).then(()=>console.log("fired"));
  }

    addMovimento(){
      this.newMovimento=true;
    }

    private createFromForm(): IDoenteHistMovimentos {
      return {
        ...new DoenteHistMovimentos(),
        data: this.editForm.get(['data']).value,
        situacao: this.editForm.get(['situacao']).value,
        obs: this.editForm.get(['obs']).value,
        doente: this.doente
      };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteHistMovimentos>>) {
      result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }
  
    protected onSaveSuccess() {
      this.isSaving = false;
    }
  
    protected onSaveError() {
      this.isSaving = false;
    }
    protected onError(errorMessage: string) {
      this.jhiAlertService.error(errorMessage, null, null);
    }
  
    trackDoenteById(index: number, item: IDoente) {
      return item.id;
    }

  ngOnInit() {
    this.isSaving = false;
    this.data.currentHistorico.subscribe((hi) => {
      this.historico=hi;
    })
    this.data.currentDoente.subscribe((doenteId) =>  {
      this.doenteId = doenteId;
      this.doenteService.find(this.doenteId).subscribe((dt) =>[
        this.doente=dt.body
      ])
      this.loadAll();
      this.registerChangeInDoenteHistMovimentos();
    });
    this.doenteService
      .query()
      .subscribe((res: HttpResponse<IDoente[]>) => (this.doentes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  loadAll() {
    this.doenteHistMovimentosService.search(this.doenteId).subscribe((res: HttpResponse<IDoenteHistMovimentos[]>) => {
      this.doenteHistMovimentos = res.body;
    });
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
    this.doenteHistMovimentosService.delete(doenteHistMovimentos.id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'doenteHistMovimentosListModification',
        content: 'Deleted an doenteHistMovimentos'
      });
    });
  }

}
