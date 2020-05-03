import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHabit } from 'app/shared/model/habit.model';

@Component({
  selector: 'jhi-habit-detail',
  templateUrl: './habit-detail.component.html'
})
export class HabitDetailComponent implements OnInit {
  habit: IHabit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ habit }) => {
      this.habit = habit;
    });
  }

  previousState() {
    window.history.back();
  }
}
