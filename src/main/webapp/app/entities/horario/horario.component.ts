import { Component, OnInit, OnDestroy } from '@angular/core';
import { DataService } from '../../data.service';
import { Subscription } from 'rxjs';
import { HorarioDoenteService } from '../horario-doente/horario-doente.service';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HorarioDoenteDeleteDialogComponent } from '../horario-doente/horario-doente-delete-dialog.component';
import { IDoente } from 'app/shared/model/doente.model';
import { JhiAlertService } from 'ng-jhipster';
import { FormBuilder, Validators } from '@angular/forms';
import { DoenteService } from '../doente/doente.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IHorarioDoente, HorarioDoente } from 'app/shared/model/horario-doente.model';
import { threadId } from 'worker_threads';



@Component({
  selector: 'jhi-horario',
  templateUrl: './horario.component.html',
  styleUrls: ['./horario.component.scss']
})
export class HorarioComponent implements OnInit, OnDestroy {
  doenteId:number;
  horarioDoentes: IHorarioDoente[];
  eventSubscriber: Subscription;
  horario: boolean;
  doente:IDoente;
  newHorario=false;
  isSaving: boolean;

  doentes: IDoente[];

  editForm = this.fb.group({
    id: [],
    dia: [],
    turno: [],
    sala: [],
    posto: [],
    duracao: [],
    doente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    private data: DataService,
    protected doenteService: DoenteService,
    protected horarioDoenteService: HorarioDoenteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private fb: FormBuilder
    ) { }


    loadAll() {
      this.horarioDoenteService.search(this.doenteId).subscribe((res: HttpResponse<IHorarioDoente[]>) => {
        this.horarioDoentes = res.body;
      });
    }

  ngOnInit() {
    this.data.currentHorario.subscribe((h) => {
      this.horario=h;
    })
    this.data.currentDoente.subscribe((doenteId) => {
      this.doenteId=doenteId;
      this.doenteService.find(this.doenteId).subscribe((dt) =>{
        this.doente= dt.body
      })
      this.loadAll();
    this.registerChangeInHorarioDoentes();
    });
    this.doenteService
      .query()
      .subscribe((res: HttpResponse<IDoente[]>) => (this.doentes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  addHorario(){
    this.newHorario=true;
  }

  save() {
    this.isSaving = true;
    const horarioDoente = this.createFromForm();
      this.subscribeToSaveResponse(this.horarioDoenteService.create(horarioDoente));
      this.newHorario=false;
      this.loadAll();
      this.loadAll();
      this.loadAll();
      this.loadAll();
      this.delay(2000)
      this.loadAll();
  }

  cancel(){
    this.newHorario=false;
    this.editForm.reset();
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(()=>resolve(), ms)).then(()=>console.log("fired"));
}

  private createFromForm(): IHorarioDoente {
    return {
      ...new HorarioDoente(),
      dia: this.editForm.get(['dia']).value,
      turno: this.editForm.get(['turno']).value,
      sala: this.editForm.get(['sala']).value,
      posto: this.editForm.get(['posto']).value,
      duracao: this.editForm.get(['duracao']).value,
      doente: this.doente
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHorarioDoente>>) {
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

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHorarioDoente) {
    return item.id;
  }

  registerChangeInHorarioDoentes() {
    this.eventSubscriber = this.eventManager.subscribe('horarioDoenteListModification', () => this.loadAll());
  }

  delete(horarioDoente: IHorarioDoente) {
    this.horarioDoenteService.delete(horarioDoente.id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'horarioDoenteListModification',
        content: 'Deleted an horarioDoente'
      });
  });
}

}
