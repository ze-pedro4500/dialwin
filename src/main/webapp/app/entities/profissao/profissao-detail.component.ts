import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfissao } from 'app/shared/model/profissao.model';

@Component({
  selector: 'jhi-profissao-detail',
  templateUrl: './profissao-detail.component.html'
})
export class ProfissaoDetailComponent implements OnInit {
  profissao: IProfissao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ profissao }) => {
      this.profissao = profissao;
    });
  }

  previousState() {
    window.history.back();
  }
}
