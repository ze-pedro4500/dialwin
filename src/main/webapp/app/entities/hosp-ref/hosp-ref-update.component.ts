import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IHospRef, HospRef } from 'app/shared/model/hosp-ref.model';
import { HospRefService } from './hosp-ref.service';

@Component({
  selector: 'jhi-hosp-ref-update',
  templateUrl: './hosp-ref-update.component.html'
})
export class HospRefUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected hospRefService: HospRefService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ hospRef }) => {
      this.updateForm(hospRef);
    });
  }

  updateForm(hospRef: IHospRef) {
    this.editForm.patchValue({
      id: hospRef.id,
      nome: hospRef.nome
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const hospRef = this.createFromForm();
    if (hospRef.id !== undefined) {
      this.subscribeToSaveResponse(this.hospRefService.update(hospRef));
    } else {
      this.subscribeToSaveResponse(this.hospRefService.create(hospRef));
    }
  }

  private createFromForm(): IHospRef {
    return {
      ...new HospRef(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospRef>>) {
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
