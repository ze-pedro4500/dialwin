import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISubSisGrupo, SubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';
import { SubSisGrupoService } from './sub-sis-grupo.service';

@Component({
  selector: 'jhi-sub-sis-grupo-update',
  templateUrl: './sub-sis-grupo-update.component.html'
})
export class SubSisGrupoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    gidDesigna: [],
    gidGrupo: []
  });

  constructor(protected subSisGrupoService: SubSisGrupoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ subSisGrupo }) => {
      this.updateForm(subSisGrupo);
    });
  }

  updateForm(subSisGrupo: ISubSisGrupo) {
    this.editForm.patchValue({
      id: subSisGrupo.id,
      gidDesigna: subSisGrupo.gidDesigna,
      gidGrupo: subSisGrupo.gidGrupo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const subSisGrupo = this.createFromForm();
    if (subSisGrupo.id !== undefined) {
      this.subscribeToSaveResponse(this.subSisGrupoService.update(subSisGrupo));
    } else {
      this.subscribeToSaveResponse(this.subSisGrupoService.create(subSisGrupo));
    }
  }

  private createFromForm(): ISubSisGrupo {
    return {
      ...new SubSisGrupo(),
      id: this.editForm.get(['id']).value,
      gidDesigna: this.editForm.get(['gidDesigna']).value,
      gidGrupo: this.editForm.get(['gidGrupo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubSisGrupo>>) {
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
