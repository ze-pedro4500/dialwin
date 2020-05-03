import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISitProf } from 'app/shared/model/sit-prof.model';

@Component({
  selector: 'jhi-sit-prof-detail',
  templateUrl: './sit-prof-detail.component.html'
})
export class SitProfDetailComponent implements OnInit {
  sitProf: ISitProf;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sitProf }) => {
      this.sitProf = sitProf;
    });
  }

  previousState() {
    window.history.back();
  }
}
