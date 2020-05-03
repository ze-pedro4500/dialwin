import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';

@Component({
  selector: 'jhi-doente-contactos-outros-detail',
  templateUrl: './doente-contactos-outros-detail.component.html'
})
export class DoenteContactosOutrosDetailComponent implements OnInit {
  doenteContactosOutros: IDoenteContactosOutros;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doenteContactosOutros }) => {
      this.doenteContactosOutros = doenteContactosOutros;
    });
  }

  previousState() {
    window.history.back();
  }
}
