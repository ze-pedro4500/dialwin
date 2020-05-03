import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITurnos, Turnos } from 'app/shared/model/turnos.model';
import { TurnosService } from './turnos.service';

@Component({
  selector: 'jhi-turnos-update',
  templateUrl: './turnos-update.component.html'
})
export class TurnosUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected turnosService: TurnosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ turnos }) => {
      this.updateForm(turnos);
    });
  }

  updateForm(turnos: ITurnos) {
    this.editForm.patchValue({
      id: turnos.id,
      nome: turnos.nome
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const turnos = this.createFromForm();
    if (turnos.id !== undefined) {
      this.subscribeToSaveResponse(this.turnosService.update(turnos));
    } else {
      this.subscribeToSaveResponse(this.turnosService.create(turnos));
    }
  }

  private createFromForm(): ITurnos {
    return {
      ...new Turnos(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITurnos>>) {
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
