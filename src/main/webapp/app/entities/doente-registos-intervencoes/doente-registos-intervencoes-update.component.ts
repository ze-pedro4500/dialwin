import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDoenteRegistosIntervencoes, DoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';
import { DoenteRegistosIntervencoesService } from './doente-registos-intervencoes.service';
import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from 'app/entities/doente/doente.service';

@Component({
  selector: 'jhi-doente-registos-intervencoes-update',
  templateUrl: './doente-registos-intervencoes-update.component.html'
})
export class DoenteRegistosIntervencoesUpdateComponent implements OnInit {
  isSaving: boolean;

  doentes: IDoente[];

  editForm = this.fb.group({
    id: [],
    descr: [],
    doente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doenteRegistosIntervencoesService: DoenteRegistosIntervencoesService,
    protected doenteService: DoenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doenteRegistosIntervencoes }) => {
      this.updateForm(doenteRegistosIntervencoes);
    });
    this.doenteService
      .query()
      .subscribe((res: HttpResponse<IDoente[]>) => (this.doentes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(doenteRegistosIntervencoes: IDoenteRegistosIntervencoes) {
    this.editForm.patchValue({
      id: doenteRegistosIntervencoes.id,
      descr: doenteRegistosIntervencoes.descr,
      doente: doenteRegistosIntervencoes.doente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenteRegistosIntervencoes = this.createFromForm();
    if (doenteRegistosIntervencoes.id !== undefined) {
      this.subscribeToSaveResponse(this.doenteRegistosIntervencoesService.update(doenteRegistosIntervencoes));
    } else {
      this.subscribeToSaveResponse(this.doenteRegistosIntervencoesService.create(doenteRegistosIntervencoes));
    }
  }

  private createFromForm(): IDoenteRegistosIntervencoes {
    return {
      ...new DoenteRegistosIntervencoes(),
      id: this.editForm.get(['id']).value,
      descr: this.editForm.get(['descr']).value,
      doente: this.editForm.get(['doente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteRegistosIntervencoes>>) {
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
