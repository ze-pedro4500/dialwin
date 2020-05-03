import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVitalidade } from 'app/shared/model/vitalidade.model';

@Component({
  selector: 'jhi-vitalidade-detail',
  templateUrl: './vitalidade-detail.component.html'
})
export class VitalidadeDetailComponent implements OnInit {
  vitalidade: IVitalidade;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vitalidade }) => {
      this.vitalidade = vitalidade;
    });
  }

  previousState() {
    window.history.back();
  }
}
