import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDoenteIdentidade, DoenteIdentidade } from 'app/shared/model/doente-identidade.model';
import { DoenteIdentidadeService } from './doente-identidade.service';
import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from 'app/entities/doente/doente.service';
import { ISubSistemas } from 'app/shared/model/sub-sistemas.model';
import { SubSistemasService } from 'app/entities/sub-sistemas/sub-sistemas.service';
import { ICentroSaude } from 'app/shared/model/centro-saude.model';
import { CentroSaudeService } from 'app/entities/centro-saude/centro-saude.service';
import { IACES } from 'app/shared/model/aces.model';
import { ACESService } from 'app/entities/aces/aces.service';
import { IHospRef } from 'app/shared/model/hosp-ref.model';
import { HospRefService } from 'app/entities/hosp-ref/hosp-ref.service';

@Component({
  selector: 'jhi-doente-identidade-update',
  templateUrl: './doente-identidade-update.component.html'
})
export class DoenteIdentidadeUpdateComponent implements OnInit {
  isSaving: boolean;

  doentes: IDoente[];

  subsistemas: ISubSistemas[];

  centrosaudes: ICentroSaude[];

  aces: IACES[];

  hosprefs: IHospRef[];
  dataNascDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    dataNasc: [],
    altura: [],
    morada: [],
    codPost: [],
    freguesia: [],
    nif: [],
    medFam: [],
    sexo: [],
    telef: [],
    telef2: [],
    docId: [],
    nBenef: [],
    nUtente: [],
    numProcHosp: [],
    doente: [],
    subsistemas: [],
    centroSaude: [],
    aCES: [],
    hospRef: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doenteIdentidadeService: DoenteIdentidadeService,
    protected doenteService: DoenteService,
    protected subSistemasService: SubSistemasService,
    protected centroSaudeService: CentroSaudeService,
    protected aCESService: ACESService,
    protected hospRefService: HospRefService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doenteIdentidade }) => {
      this.updateForm(doenteIdentidade);
    });
    this.doenteService.query({ filter: 'doenteidentidade-is-null' }).subscribe(
      (res: HttpResponse<IDoente[]>) => {
        if (!this.editForm.get('doente').value || !this.editForm.get('doente').value.id) {
          this.doentes = res.body;
        } else {
          this.doenteService
            .find(this.editForm.get('doente').value.id)
            .subscribe(
              (subRes: HttpResponse<IDoente>) => (this.doentes = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.subSistemasService
      .query()
      .subscribe(
        (res: HttpResponse<ISubSistemas[]>) => (this.subsistemas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.centroSaudeService
      .query()
      .subscribe(
        (res: HttpResponse<ICentroSaude[]>) => (this.centrosaudes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.aCESService
      .query()
      .subscribe((res: HttpResponse<IACES[]>) => (this.aces = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.hospRefService
      .query()
      .subscribe((res: HttpResponse<IHospRef[]>) => (this.hosprefs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(doenteIdentidade: IDoenteIdentidade) {
    this.editForm.patchValue({
      id: doenteIdentidade.id,
      nome: doenteIdentidade.nome,
      dataNasc: doenteIdentidade.dataNasc,
      altura: doenteIdentidade.altura,
      morada: doenteIdentidade.morada,
      codPost: doenteIdentidade.codPost,
      freguesia: doenteIdentidade.freguesia,
      nif: doenteIdentidade.nif,
      medFam: doenteIdentidade.medFam,
      sexo: doenteIdentidade.sexo,
      telef: doenteIdentidade.telef,
      telef2: doenteIdentidade.telef2,
      docId: doenteIdentidade.docId,
      nBenef: doenteIdentidade.nBenef,
      nUtente: doenteIdentidade.nUtente,
      numProcHosp: doenteIdentidade.numProcHosp,
      doente: doenteIdentidade.doente,
      subsistemas: doenteIdentidade.subsistemas,
      centroSaude: doenteIdentidade.centroSaude,
      aCES: doenteIdentidade.aCES,
      hospRef: doenteIdentidade.hospRef
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenteIdentidade = this.createFromForm();
    if (doenteIdentidade.id !== undefined) {
      this.subscribeToSaveResponse(this.doenteIdentidadeService.update(doenteIdentidade));
    } else {
      this.subscribeToSaveResponse(this.doenteIdentidadeService.create(doenteIdentidade));
    }
  }

  private createFromForm(): IDoenteIdentidade {
    return {
      ...new DoenteIdentidade(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      dataNasc: this.editForm.get(['dataNasc']).value,
      altura: this.editForm.get(['altura']).value,
      morada: this.editForm.get(['morada']).value,
      codPost: this.editForm.get(['codPost']).value,
      freguesia: this.editForm.get(['freguesia']).value,
      nif: this.editForm.get(['nif']).value,
      medFam: this.editForm.get(['medFam']).value,
      sexo: this.editForm.get(['sexo']).value,
      telef: this.editForm.get(['telef']).value,
      telef2: this.editForm.get(['telef2']).value,
      docId: this.editForm.get(['docId']).value,
      nBenef: this.editForm.get(['nBenef']).value,
      nUtente: this.editForm.get(['nUtente']).value,
      numProcHosp: this.editForm.get(['numProcHosp']).value,
      doente: this.editForm.get(['doente']).value,
      subsistemas: this.editForm.get(['subsistemas']).value,
      centroSaude: this.editForm.get(['centroSaude']).value,
      aCES: this.editForm.get(['aCES']).value,
      hospRef: this.editForm.get(['hospRef']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteIdentidade>>) {
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

  trackSubSistemasById(index: number, item: ISubSistemas) {
    return item.id;
  }

  trackCentroSaudeById(index: number, item: ICentroSaude) {
    return item.id;
  }

  trackACESById(index: number, item: IACES) {
    return item.id;
  }

  trackHospRefById(index: number, item: IHospRef) {
    return item.id;
  }
}
