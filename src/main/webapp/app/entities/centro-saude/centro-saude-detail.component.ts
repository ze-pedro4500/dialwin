import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICentroSaude } from 'app/shared/model/centro-saude.model';

@Component({
  selector: 'jhi-centro-saude-detail',
  templateUrl: './centro-saude-detail.component.html'
})
export class CentroSaudeDetailComponent implements OnInit {
  centroSaude: ICentroSaude;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ centroSaude }) => {
      this.centroSaude = centroSaude;
    });
  }

  previousState() {
    window.history.back();
  }
}
