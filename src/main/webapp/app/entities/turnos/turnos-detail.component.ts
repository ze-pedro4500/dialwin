import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITurnos } from 'app/shared/model/turnos.model';

@Component({
  selector: 'jhi-turnos-detail',
  templateUrl: './turnos-detail.component.html'
})
export class TurnosDetailComponent implements OnInit {
  turnos: ITurnos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ turnos }) => {
      this.turnos = turnos;
    });
  }

  previousState() {
    window.history.back();
  }
}
