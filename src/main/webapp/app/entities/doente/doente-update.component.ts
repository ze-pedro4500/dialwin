import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDoente, Doente } from 'app/shared/model/doente.model';
import { DoenteService } from './doente.service';
import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';
import { DoenteIdentidadeService } from 'app/entities/doente-identidade/doente-identidade.service';
import { IDoenteContactos } from 'app/shared/model/doente-contactos.model';
import { DoenteContactosService } from 'app/entities/doente-contactos/doente-contactos.service';
import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';
import { DoenteSocioFamiliarService } from 'app/entities/doente-socio-familiar/doente-socio-familiar.service';
import { ITurnos } from 'app/shared/model/turnos.model';
import { TurnosService } from 'app/entities/turnos/turnos.service';

@Component({
  selector: 'jhi-doente-update',
  templateUrl: './doente-update.component.html'
})
export class DoenteUpdateComponent implements OnInit {
  isSaving: boolean;

  doenteidentidades: IDoenteIdentidade[];

  doentecontactos: IDoenteContactos[];

  doentesociofamiliars: IDoenteSocioFamiliar[];

  turnos: ITurnos[];

  editForm = this.fb.group({
    id: [],
    situacao: [],
    turnos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doenteService: DoenteService,
    protected doenteIdentidadeService: DoenteIdentidadeService,
    protected doenteContactosService: DoenteContactosService,
    protected doenteSocioFamiliarService: DoenteSocioFamiliarService,
    protected turnosService: TurnosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doente }) => {
      this.updateForm(doente);
    });
    this.doenteIdentidadeService
      .query()
      .subscribe(
        (res: HttpResponse<IDoenteIdentidade[]>) => (this.doenteidentidades = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.doenteContactosService
      .query()
      .subscribe(
        (res: HttpResponse<IDoenteContactos[]>) => (this.doentecontactos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.doenteSocioFamiliarService
      .query()
      .subscribe(
        (res: HttpResponse<IDoenteSocioFamiliar[]>) => (this.doentesociofamiliars = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.turnosService
      .query()
      .subscribe((res: HttpResponse<ITurnos[]>) => (this.turnos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(doente: IDoente) {
    this.editForm.patchValue({
      id: doente.id,
      situacao: doente.situacao,
      turnos: doente.turnos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doente = this.createFromForm();
    if (doente.id !== undefined) {
      this.subscribeToSaveResponse(this.doenteService.update(doente));
    } else {
      this.subscribeToSaveResponse(this.doenteService.create(doente));
    }
  }

  private createFromForm(): IDoente {
    return {
      ...new Doente(),
      id: this.editForm.get(['id']).value,
      situacao: this.editForm.get(['situacao']).value,
      turnos: this.editForm.get(['turnos']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoente>>) {
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

  trackDoenteIdentidadeById(index: number, item: IDoenteIdentidade) {
    return item.id;
  }

  trackDoenteContactosById(index: number, item: IDoenteContactos) {
    return item.id;
  }

  trackDoenteSocioFamiliarById(index: number, item: IDoenteSocioFamiliar) {
    return item.id;
  }

  trackTurnosById(index: number, item: ITurnos) {
    return item.id;
  }
}
