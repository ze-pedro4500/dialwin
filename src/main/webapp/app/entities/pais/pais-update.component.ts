import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPais, Pais } from 'app/shared/model/pais.model';
import { PaisService } from './pais.service';
import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';
import { DoenteIdentidadeService } from 'app/entities/doente-identidade/doente-identidade.service';

@Component({
  selector: 'jhi-pais-update',
  templateUrl: './pais-update.component.html'
})
export class PaisUpdateComponent implements OnInit {
  isSaving: boolean;

  doenteidentidades: IDoenteIdentidade[];

  editForm = this.fb.group({
    id: [],
    nome: [],
    sigla: [],
    doenteIdentidade: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected paisService: PaisService,
    protected doenteIdentidadeService: DoenteIdentidadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pais }) => {
      this.updateForm(pais);
    });
    this.doenteIdentidadeService
      .query()
      .subscribe(
        (res: HttpResponse<IDoenteIdentidade[]>) => (this.doenteidentidades = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(pais: IPais) {
    this.editForm.patchValue({
      id: pais.id,
      nome: pais.nome,
      sigla: pais.sigla,
      doenteIdentidade: pais.doenteIdentidade
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pais = this.createFromForm();
    if (pais.id !== undefined) {
      this.subscribeToSaveResponse(this.paisService.update(pais));
    } else {
      this.subscribeToSaveResponse(this.paisService.create(pais));
    }
  }

  private createFromForm(): IPais {
    return {
      ...new Pais(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      sigla: this.editForm.get(['sigla']).value,
      doenteIdentidade: this.editForm.get(['doenteIdentidade']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPais>>) {
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
}
