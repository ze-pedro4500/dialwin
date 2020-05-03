import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

@Component({
  selector: 'jhi-doente-socio-familiar-detail',
  templateUrl: './doente-socio-familiar-detail.component.html'
})
export class DoenteSocioFamiliarDetailComponent implements OnInit {
  doenteSocioFamiliar: IDoenteSocioFamiliar;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doenteSocioFamiliar }) => {
      this.doenteSocioFamiliar = doenteSocioFamiliar;
    });
  }

  previousState() {
    window.history.back();
  }
}
