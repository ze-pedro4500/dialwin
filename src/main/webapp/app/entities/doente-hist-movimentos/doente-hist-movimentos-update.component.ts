import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDoenteHistMovimentos, DoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';
import { DoenteHistMovimentosService } from './doente-hist-movimentos.service';
import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from 'app/entities/doente/doente.service';

@Component({
  selector: 'jhi-doente-hist-movimentos-update',
  templateUrl: './doente-hist-movimentos-update.component.html'
})
export class DoenteHistMovimentosUpdateComponent implements OnInit {
  isSaving: boolean;

  doentes: IDoente[];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    data: [],
    situacao: [],
    obs: [],
    doente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doenteHistMovimentosService: DoenteHistMovimentosService,
    protected doenteService: DoenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doenteHistMovimentos }) => {
      this.updateForm(doenteHistMovimentos);
    });
    this.doenteService
      .query()
      .subscribe((res: HttpResponse<IDoente[]>) => (this.doentes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(doenteHistMovimentos: IDoenteHistMovimentos) {
    this.editForm.patchValue({
      id: doenteHistMovimentos.id,
      data: doenteHistMovimentos.data,
      situacao: doenteHistMovimentos.situacao,
      obs: doenteHistMovimentos.obs,
      doente: doenteHistMovimentos.doente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenteHistMovimentos = this.createFromForm();
    if (doenteHistMovimentos.id !== undefined) {
      this.subscribeToSaveResponse(this.doenteHistMovimentosService.update(doenteHistMovimentos));
    } else {
      this.subscribeToSaveResponse(this.doenteHistMovimentosService.create(doenteHistMovimentos));
    }
  }

  private createFromForm(): IDoenteHistMovimentos {
    return {
      ...new DoenteHistMovimentos(),
      id: this.editForm.get(['id']).value,
      data: this.editForm.get(['data']).value,
      situacao: this.editForm.get(['situacao']).value,
      obs: this.editForm.get(['obs']).value,
      doente: this.editForm.get(['doente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteHistMovimentos>>) {
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
