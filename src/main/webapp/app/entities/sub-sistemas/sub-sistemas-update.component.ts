import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ISubSistemas, SubSistemas } from 'app/shared/model/sub-sistemas.model';
import { SubSistemasService } from './sub-sistemas.service';
import { ISubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';
import { SubSisGrupoService } from 'app/entities/sub-sis-grupo/sub-sis-grupo.service';

@Component({
  selector: 'jhi-sub-sistemas-update',
  templateUrl: './sub-sistemas-update.component.html'
})
export class SubSistemasUpdateComponent implements OnInit {
  isSaving: boolean;

  subsisgrupos: ISubSisGrupo[];

  editForm = this.fb.group({
    id: [],
    gidNome: [],
    gidCode: [],
    subSisGrupo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected subSistemasService: SubSistemasService,
    protected subSisGrupoService: SubSisGrupoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ subSistemas }) => {
      this.updateForm(subSistemas);
    });
    this.subSisGrupoService
      .query()
      .subscribe(
        (res: HttpResponse<ISubSisGrupo[]>) => (this.subsisgrupos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(subSistemas: ISubSistemas) {
    this.editForm.patchValue({
      id: subSistemas.id,
      gidNome: subSistemas.gidNome,
      gidCode: subSistemas.gidCode,
      subSisGrupo: subSistemas.subSisGrupo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const subSistemas = this.createFromForm();
    if (subSistemas.id !== undefined) {
      this.subscribeToSaveResponse(this.subSistemasService.update(subSistemas));
    } else {
      this.subscribeToSaveResponse(this.subSistemasService.create(subSistemas));
    }
  }

  private createFromForm(): ISubSistemas {
    return {
      ...new SubSistemas(),
      id: this.editForm.get(['id']).value,
      gidNome: this.editForm.get(['gidNome']).value,
      gidCode: this.editForm.get(['gidCode']).value,
      subSisGrupo: this.editForm.get(['subSisGrupo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubSistemas>>) {
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

  trackSubSisGrupoById(index: number, item: ISubSisGrupo) {
    return item.id;
  }
}
