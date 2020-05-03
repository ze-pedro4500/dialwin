import { Component, OnInit } from '@angular/core';
import { IDoente } from 'app/shared/model/doente.model';
import { IVitalidade } from 'app/shared/model/vitalidade.model';
import { IHabit } from 'app/shared/model/habit.model';
import { IGrauConf } from 'app/shared/model/grau-conf.model';
import { IProfissao } from 'app/shared/model/profissao.model';
import { ISitProf } from 'app/shared/model/sit-prof.model';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiAlertService } from 'ng-jhipster';
import { DoenteSocioFamiliarService } from '../doente-socio-familiar/doente-socio-familiar.service';
import { DoenteService } from 'app/entities/doente/doente.service';
import { VitalidadeService } from 'app/entities/vitalidade/vitalidade.service';
import { HabitService } from 'app/entities/habit/habit.service';
import { GrauConfService } from 'app/entities/grau-conf/grau-conf.service';
import { ProfissaoService } from 'app/entities/profissao/profissao.service';
import { SitProfService } from 'app/entities/sit-prof/sit-prof.service';
import { DataService } from '../../data.service'
import { IDoenteSocioFamiliar, DoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-sociofamiliar',
  templateUrl: './sociofamiliar.component.html',
  styleUrls: ['./sociofamiliar.component.scss']
})
export class SociofamiliarComponent implements OnInit {


  doenteId: number;
  isSaving: boolean;

  doentes: IDoente[];

  vitalidades: IVitalidade[];

  habits: IHabit[];

  grauconfs: IGrauConf[];

  profissaos: IProfissao[];

  sitprofs: ISitProf[];

  socioFamiliar:boolean;

  editForm = this.fb.group({
    id: [],
    habilitacoes: [],
    estCivil: [],
    conjugeNome: [],
    conjugeProf: [],
    activTempLiv: [],
    habitacaoObs: [],
    grauConfortoObs: [],
    respostaSocial: [],
    doente: [],
    vitalidade: [],
    habit: [],
    grauConf: [],
    profissao: [],
    sitProf: []
  });
  constructor(protected jhiAlertService: JhiAlertService,
    protected doenteSocioFamiliarService: DoenteSocioFamiliarService,
    protected doenteService: DoenteService,
    protected vitalidadeService: VitalidadeService,
    protected habitService: HabitService,
    protected grauConfService: GrauConfService,
    protected profissaoService: ProfissaoService,
    protected sitProfService: SitProfService,
    private fb: FormBuilder,
    private data: DataService) { }

  ngOnInit() {
    this.data.currentSocioFamiliar.subscribe((sf) => {
      this.socioFamiliar = sf;
    })
    if(this.doenteId ===0){
      this.socioFamiliar=false;
    }
    this.isSaving=false;
    this.data.currentDoente.subscribe((doenteId) => {
      this.doenteId=doenteId;
      this.doenteSocioFamiliarService.search(this.doenteId).subscribe((resp) => {
        this.updateForm(resp.body);
      });
    });
    this.doenteService.query({ filter: 'doentesociofamiliar-is-null' }).subscribe(
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
    this.vitalidadeService
      .query()
      .subscribe(
        (res: HttpResponse<IVitalidade[]>) => (this.vitalidades = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.habitService
      .query()
      .subscribe((res: HttpResponse<IHabit[]>) => (this.habits = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.grauConfService
      .query()
      .subscribe((res: HttpResponse<IGrauConf[]>) => (this.grauconfs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.profissaoService
      .query()
      .subscribe((res: HttpResponse<IProfissao[]>) => (this.profissaos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.sitProfService
      .query()
      .subscribe((res: HttpResponse<ISitProf[]>) => (this.sitprofs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }


  updateForm(doenteSocioFamiliar: IDoenteSocioFamiliar) {
    this.editForm.patchValue({
      id: doenteSocioFamiliar.id,
      habilitacoes: doenteSocioFamiliar.habilitacoes,
      estCivil: doenteSocioFamiliar.estCivil,
      conjugeNome: doenteSocioFamiliar.conjugeNome,
      conjugeProf: doenteSocioFamiliar.conjugeProf,
      activTempLiv: doenteSocioFamiliar.activTempLiv,
      habitacaoObs: doenteSocioFamiliar.habitacaoObs,
      grauConfortoObs: doenteSocioFamiliar.grauConfortoObs,
      respostaSocial: doenteSocioFamiliar.respostaSocial,
      doente: doenteSocioFamiliar.doente,
      vitalidade: doenteSocioFamiliar.vitalidade,
      habit: doenteSocioFamiliar.habit,
      grauConf: doenteSocioFamiliar.grauConf,
      profissao: doenteSocioFamiliar.profissao,
      sitProf: doenteSocioFamiliar.sitProf
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenteSocioFamiliar = this.createFromForm();
    if (doenteSocioFamiliar.id !== undefined) {
      this.subscribeToSaveResponse(this.doenteSocioFamiliarService.update(doenteSocioFamiliar));
    } else {
      this.subscribeToSaveResponse(this.doenteSocioFamiliarService.create(doenteSocioFamiliar));
    }
  }

  private createFromForm(): IDoenteSocioFamiliar {
    return {
      ...new DoenteSocioFamiliar(),
      id: this.editForm.get(['id']).value,
      habilitacoes: this.editForm.get(['habilitacoes']).value,
      estCivil: this.editForm.get(['estCivil']).value,
      conjugeNome: this.editForm.get(['conjugeNome']).value,
      conjugeProf: this.editForm.get(['conjugeProf']).value,
      activTempLiv: this.editForm.get(['activTempLiv']).value,
      habitacaoObs: this.editForm.get(['habitacaoObs']).value,
      grauConfortoObs: this.editForm.get(['grauConfortoObs']).value,
      respostaSocial: this.editForm.get(['respostaSocial']).value,
      doente: this.editForm.get(['doente']).value,
      vitalidade: this.editForm.get(['vitalidade']).value,
      habit: this.editForm.get(['habit']).value,
      grauConf: this.editForm.get(['grauConf']).value,
      profissao: this.editForm.get(['profissao']).value,
      sitProf: this.editForm.get(['sitProf']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteSocioFamiliar>>) {
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

  trackVitalidadeById(index: number, item: IVitalidade) {
    return item.id;
  }

  trackHabitById(index: number, item: IHabit) {
    return item.id;
  }

  trackGrauConfById(index: number, item: IGrauConf) {
    return item.id;
  }

  trackProfissaoById(index: number, item: IProfissao) {
    return item.id;
  }

  trackSitProfById(index: number, item: ISitProf) {
    return item.id;
  }
}
