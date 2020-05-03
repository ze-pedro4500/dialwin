import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoenteContactos } from 'app/shared/model/doente-contactos.model';

@Component({
  selector: 'jhi-doente-contactos-detail',
  templateUrl: './doente-contactos-detail.component.html'
})
export class DoenteContactosDetailComponent implements OnInit {
  doenteContactos: IDoenteContactos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doenteContactos }) => {
      this.doenteContactos = doenteContactos;
    });
  }

  previousState() {
    window.history.back();
  }
}
