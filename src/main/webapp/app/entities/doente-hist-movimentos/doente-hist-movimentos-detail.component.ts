import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';

@Component({
  selector: 'jhi-doente-hist-movimentos-detail',
  templateUrl: './doente-hist-movimentos-detail.component.html'
})
export class DoenteHistMovimentosDetailComponent implements OnInit {
  doenteHistMovimentos: IDoenteHistMovimentos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doenteHistMovimentos }) => {
      this.doenteHistMovimentos = doenteHistMovimentos;
    });
  }

  previousState() {
    window.history.back();
  }
}
