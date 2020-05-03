import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IHabit, Habit } from 'app/shared/model/habit.model';
import { HabitService } from './habit.service';

@Component({
  selector: 'jhi-habit-update',
  templateUrl: './habit-update.component.html'
})
export class HabitUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected habitService: HabitService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ habit }) => {
      this.updateForm(habit);
    });
  }

  updateForm(habit: IHabit) {
    this.editForm.patchValue({
      id: habit.id,
      nome: habit.nome
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const habit = this.createFromForm();
    if (habit.id !== undefined) {
      this.subscribeToSaveResponse(this.habitService.update(habit));
    } else {
      this.subscribeToSaveResponse(this.habitService.create(habit));
    }
  }

  private createFromForm(): IHabit {
    return {
      ...new Habit(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHabit>>) {
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
