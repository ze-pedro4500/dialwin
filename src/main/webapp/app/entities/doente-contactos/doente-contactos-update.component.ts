import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDoenteContactos, DoenteContactos } from 'app/shared/model/doente-contactos.model';
import { DoenteContactosService } from './doente-contactos.service';
import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from 'app/entities/doente/doente.service';

@Component({
  selector: 'jhi-doente-contactos-update',
  templateUrl: './doente-contactos-update.component.html'
})
export class DoenteContactosUpdateComponent implements OnInit {
  isSaving: boolean;

  doentes: IDoente[];

  editForm = this.fb.group({
    id: [],
    transportador: [],
    telefTransp: [],
    doente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doenteContactosService: DoenteContactosService,
    protected doenteService: DoenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doenteContactos }) => {
      this.updateForm(doenteContactos);
    });
    this.doenteService.query({ filter: 'doentecontactos-is-null' }).subscribe(
      (res: HttpResponse<IDoente[]>) => {
        if (!this.editForm.get('doente').value || !this.editForm.get('doente').value.id) {
          this.doentes = res.body;
        } else {
          this.doenteService
            .find(this.editForm.get('doente').value.id)
            .subscribe(
              (subRes: HttpResponse<IDoente>) => (this.doentes = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(doenteContactos: IDoenteContactos) {
    this.editForm.patchValue({
      id: doenteContactos.id,
      transportador: doenteContactos.transportador,
      telefTransp: doenteContactos.telefTransp,
      doente: doenteContactos.doente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenteContactos = this.createFromForm();
    if (doenteContactos.id !== undefined) {
      this.subscribeToSaveResponse(this.doenteContactosService.update(doenteContactos));
    } else {
      this.subscribeToSaveResponse(this.doenteContactosService.create(doenteContactos));
    }
  }

  private createFromForm(): IDoenteContactos {
    return {
      ...new DoenteContactos(),
      id: this.editForm.get(['id']).value,
      transportador: this.editForm.get(['transportador']).value,
      telefTransp: this.editForm.get(['telefTransp']).value,
      doente: this.editForm.get(['doente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteContactos>>) {
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
