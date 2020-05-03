import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IGrauConf, GrauConf } from 'app/shared/model/grau-conf.model';
import { GrauConfService } from './grau-conf.service';

@Component({
  selector: 'jhi-grau-conf-update',
  templateUrl: './grau-conf-update.component.html'
})
export class GrauConfUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected grauConfService: GrauConfService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ grauConf }) => {
      this.updateForm(grauConf);
    });
  }

  updateForm(grauConf: IGrauConf) {
    this.editForm.patchValue({
      id: grauConf.id,
      nome: grauConf.nome
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const grauConf = this.createFromForm();
    if (grauConf.id !== undefined) {
      this.subscribeToSaveResponse(this.grauConfService.update(grauConf));
    } else {
      this.subscribeToSaveResponse(this.grauConfService.create(grauConf));
    }
  }

  private createFromForm(): IGrauConf {
    return {
      ...new GrauConf(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrauConf>>) {
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
