import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IHorarioDoente, HorarioDoente } from 'app/shared/model/horario-doente.model';
import { HorarioDoenteService } from './horario-doente.service';
import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from 'app/entities/doente/doente.service';

@Component({
  selector: 'jhi-horario-doente-update',
  templateUrl: './horario-doente-update.component.html'
})
export class HorarioDoenteUpdateComponent implements OnInit {
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
    protected horarioDoenteService: HorarioDoenteService,
    protected doenteService: DoenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ horarioDoente }) => {
      this.updateForm(horarioDoente);
    });
    this.doenteService
      .query()
      .subscribe((res: HttpResponse<IDoente[]>) => (this.doentes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(horarioDoente: IHorarioDoente) {
    this.editForm.patchValue({
      id: horarioDoente.id,
      dia: horarioDoente.dia,
      turno: horarioDoente.turno,
      sala: horarioDoente.sala,
      posto: horarioDoente.posto,
      duracao: horarioDoente.duracao,
      doente: horarioDoente.doente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const horarioDoente = this.createFromForm();
    if (horarioDoente.id !== undefined) {
      this.subscribeToSaveResponse(this.horarioDoenteService.update(horarioDoente));
    } else {
      this.subscribeToSaveResponse(this.horarioDoenteService.create(horarioDoente));
    }
  }

  private createFromForm(): IHorarioDoente {
    return {
      ...new HorarioDoente(),
      id: this.editForm.get(['id']).value,
      dia: this.editForm.get(['dia']).value,
      turno: this.editForm.get(['turno']).value,
      sala: this.editForm.get(['sala']).value,
      posto: this.editForm.get(['posto']).value,
      duracao: this.editForm.get(['duracao']).value,
      doente: this.editForm.get(['doente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHorarioDoente>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
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
}
