import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVitalidade, Vitalidade } from 'app/shared/model/vitalidade.model';
import { VitalidadeService } from './vitalidade.service';

@Component({
  selector: 'jhi-vitalidade-update',
  templateUrl: './vitalidade-update.component.html'
})
export class VitalidadeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [],
    percentagem: []
  });

  constructor(protected vitalidadeService: VitalidadeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vitalidade }) => {
      this.updateForm(vitalidade);
    });
  }

  updateForm(vitalidade: IVitalidade) {
    this.editForm.patchValue({
      id: vitalidade.id,
      nome: vitalidade.nome,
      percentagem: vitalidade.percentagem
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vitalidade = this.createFromForm();
    if (vitalidade.id !== undefined) {
      this.subscribeToSaveResponse(this.vitalidadeService.update(vitalidade));
    } else {
      this.subscribeToSaveResponse(this.vitalidadeService.create(vitalidade));
    }
  }

  private createFromForm(): IVitalidade {
    return {
      ...new Vitalidade(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      percentagem: this.editForm.get(['percentagem']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVitalidade>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
