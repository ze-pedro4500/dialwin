import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoente } from 'app/shared/model/doente.model';

@Component({
  selector: 'jhi-doente-detail',
  templateUrl: './doente-detail.component.html'
})
export class DoenteDetailComponent implements OnInit {
  doente: IDoente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doente }) => {
      this.doente = doente;
    });
  }

  previousState() {
    window.history.back();
  }
}
