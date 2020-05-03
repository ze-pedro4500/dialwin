import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHorarioDoente } from 'app/shared/model/horario-doente.model';

@Component({
  selector: 'jhi-horario-doente-detail',
  templateUrl: './horario-doente-detail.component.html'
})
export class HorarioDoenteDetailComponent implements OnInit {
  horarioDoente: IHorarioDoente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ horarioDoente }) => {
      this.horarioDoente = horarioDoente;
    });
  }

  previousState() {
    window.history.back();
  }
}
