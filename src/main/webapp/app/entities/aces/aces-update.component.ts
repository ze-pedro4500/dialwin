import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IACES, ACES } from 'app/shared/model/aces.model';
import { ACESService } from './aces.service';

@Component({
  selector: 'jhi-aces-update',
  templateUrl: './aces-update.component.html'
})
export class ACESUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected aCESService: ACESService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aCES }) => {
      this.updateForm(aCES);
    });
  }

  updateForm(aCES: IACES) {
    this.editForm.patchValue({
      id: aCES.id,
      nome: aCES.nome
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const aCES = this.createFromForm();
    if (aCES.id !== undefined) {
      this.subscribeToSaveResponse(this.aCESService.update(aCES));
    } else {
      this.subscribeToSaveResponse(this.aCESService.create(aCES));
    }
  }

  private createFromForm(): IACES {
    return {
      ...new ACES(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IACES>>) {
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
