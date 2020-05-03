import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubSistemas } from 'app/shared/model/sub-sistemas.model';

@Component({
  selector: 'jhi-sub-sistemas-detail',
  templateUrl: './sub-sistemas-detail.component.html'
})
export class SubSistemasDetailComponent implements OnInit {
  subSistemas: ISubSistemas;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subSistemas }) => {
      this.subSistemas = subSistemas;
    });
  }

  previousState() {
    window.history.back();
  }
}
