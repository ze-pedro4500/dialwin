import { Component, OnInit } from '@angular/core';
import { DataService } from '../../data.service'
import { DoenteIdentidadeService } from '../doente-identidade/doente-identidade.service'
import { IDoenteIdentidade, DoenteIdentidade } from '../../shared/model/doente-identidade.model'
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IDoente } from '../../shared/model/doente.model'
import { ISubSistemas } from 'app/shared/model/sub-sistemas.model';
import { ICentroSaude } from 'app/shared/model/centro-saude.model';
import { IACES } from 'app/shared/model/aces.model';
import { IHospRef } from 'app/shared/model/hosp-ref.model';
import { FormBuilder} from '@angular/forms';
import { DoenteService } from '../doente/doente.service';
import { HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { SubSistemasService } from 'app/entities/sub-sistemas/sub-sistemas.service';
import { CentroSaudeService } from 'app/entities/centro-saude/centro-saude.service';
import { ACESService } from 'app/entities/aces/aces.service';
import { HospRefService } from 'app/entities/hosp-ref/hosp-ref.service';


@Component({
  selector: 'jhi-demoid',
  templateUrl: './demoid.component.html',
  styleUrls: ['./demoid.component.scss']
})
export class DemoidComponent implements OnInit {
  identidade:boolean;
  isSaving: boolean;
  doentes: IDoente[];
  subsistemas: ISubSistemas[];
  centrosaudes: ICentroSaude[];
  aces:IACES[];
  hosprefs: IHospRef[];
  doenteidentidade: IDoenteIdentidade;
  dataNascDp: any;
  doenteId:number;
  nome:string;
  dataNascimento:string;
  altura:number;
  sexo:string;
  morada:string;
  telefone:number;
  telemovel:number;
  codigoPostal:string;
  freguesia:string;
  nif:number;
  docIdentif:number;
  subSis: string;
  numBenef:number;
  centroSaude: string;
  numUtente:number;
  numGid:number;
  medicoFamilia:string;
  hospRef:string;
  numProc:number;

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
    private data: DataService,
    protected jhiAlertService: JhiAlertService,
    protected subSistemasService: SubSistemasService,
    protected centroSaudeService: CentroSaudeService,
    protected aCESService: ACESService,
    protected hospRefService: HospRefService,
    protected doenteIdentidadeService: DoenteIdentidadeService,
    private fb: FormBuilder,
    protected doenteService: DoenteService) { }
  

  ngOnInit() {
    this.isSaving=false;
    this.data.currentIdentidade.subscribe(identidade => this.identidade = identidade);
    this.data.currentDoente.subscribe((doenteId) =>  {
      this.doenteId = doenteId;
      this.doenteIdentidadeService.find(doenteId).subscribe((resp) => {
        this.updateForm(resp.body);
      });
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
