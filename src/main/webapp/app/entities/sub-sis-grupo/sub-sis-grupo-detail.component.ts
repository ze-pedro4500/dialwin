import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';

@Component({
  selector: 'jhi-sub-sis-grupo-detail',
  templateUrl: './sub-sis-grupo-detail.component.html'
})
export class SubSisGrupoDetailComponent implements OnInit {
  subSisGrupo: ISubSisGrupo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subSisGrupo }) => {
      this.subSisGrupo = subSisGrupo;
    });
  }

  previousState() {
    window.history.back();
  }
}
