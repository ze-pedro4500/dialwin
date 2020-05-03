import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRegistoData, RegistoData } from 'app/shared/model/registo-data.model';
import { RegistoDataService } from './registo-data.service';

@Component({
  selector: 'jhi-registo-data-update',
  templateUrl: './registo-data-update.component.html'
})
export class RegistoDataUpdateComponent implements OnInit {
  isSaving = false;
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    registo: [],
    data: []
  });

  constructor(protected registoDataService: RegistoDataService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ registoData }) => {
      this.updateForm(registoData);
    });
  }

  updateForm(registoData: IRegistoData): void {
    this.editForm.patchValue({
      id: registoData.id,
      registo: registoData.registo,
      data: registoData.data
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const registoData = this.createFromForm();
    if (registoData.id !== undefined) {
      this.subscribeToSaveResponse(this.registoDataService.update(registoData));
    } else {
      this.subscribeToSaveResponse(this.registoDataService.create(registoData));
    }
  }

  private createFromForm(): IRegistoData {
    return {
      ...new RegistoData(),
      id: this.editForm.get(['id'])!.value,
      registo: this.editForm.get(['registo'])!.value,
      data: this.editForm.get(['data'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegistoData>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
