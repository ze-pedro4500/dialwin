import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IACES } from 'app/shared/model/aces.model';

@Component({
  selector: 'jhi-aces-detail',
  templateUrl: './aces-detail.component.html'
})
export class ACESDetailComponent implements OnInit {
  aCES: IACES;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aCES }) => {
      this.aCES = aCES;
    });
  }

  previousState() {
    window.history.back();
  }
}
