import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProfissao, Profissao } from 'app/shared/model/profissao.model';
import { ProfissaoService } from './profissao.service';

@Component({
  selector: 'jhi-profissao-update',
  templateUrl: './profissao-update.component.html'
})
export class ProfissaoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected profissaoService: ProfissaoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profissao }) => {
      this.updateForm(profissao);
    });
  }

  updateForm(profissao: IProfissao) {
    this.editForm.patchValue({
      id: profissao.id,
      nome: profissao.nome
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profissao = this.createFromForm();
    if (profissao.id !== undefined) {
      this.subscribeToSaveResponse(this.profissaoService.update(profissao));
    } else {
      this.subscribeToSaveResponse(this.profissaoService.create(profissao));
    }
  }

  private createFromForm(): IProfissao {
    return {
      ...new Profissao(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfissao>>) {
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
