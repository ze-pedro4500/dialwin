import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDoenteDiagnosticoSocial, DoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';
import { DoenteDiagnosticoSocialService } from './doente-diagnostico-social.service';
import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from 'app/entities/doente/doente.service';

@Component({
  selector: 'jhi-doente-diagnostico-social-update',
  templateUrl: './doente-diagnostico-social-update.component.html'
})
export class DoenteDiagnosticoSocialUpdateComponent implements OnInit {
  isSaving: boolean;

  doentes: IDoente[];

  editForm = this.fb.group({
    id: [],
    descr: [],
    doente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doenteDiagnosticoSocialService: DoenteDiagnosticoSocialService,
    protected doenteService: DoenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doenteDiagnosticoSocial }) => {
      this.updateForm(doenteDiagnosticoSocial);
    });
    this.doenteService
      .query()
      .subscribe((res: HttpResponse<IDoente[]>) => (this.doentes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(doenteDiagnosticoSocial: IDoenteDiagnosticoSocial) {
    this.editForm.patchValue({
      id: doenteDiagnosticoSocial.id,
      descr: doenteDiagnosticoSocial.descr,
      doente: doenteDiagnosticoSocial.doente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenteDiagnosticoSocial = this.createFromForm();
    if (doenteDiagnosticoSocial.id !== undefined) {
      this.subscribeToSaveResponse(this.doenteDiagnosticoSocialService.update(doenteDiagnosticoSocial));
    } else {
      this.subscribeToSaveResponse(this.doenteDiagnosticoSocialService.create(doenteDiagnosticoSocial));
    }
  }

  private createFromForm(): IDoenteDiagnosticoSocial {
    return {
      ...new DoenteDiagnosticoSocial(),
      id: this.editForm.get(['id']).value,
      descr: this.editForm.get(['descr']).value,
      doente: this.editForm.get(['doente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteDiagnosticoSocial>>) {
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
