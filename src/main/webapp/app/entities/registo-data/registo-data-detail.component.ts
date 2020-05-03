import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegistoData } from 'app/shared/model/registo-data.model';

@Component({
  selector: 'jhi-registo-data-detail',
  templateUrl: './registo-data-detail.component.html'
})
export class RegistoDataDetailComponent implements OnInit {
  registoData: IRegistoData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ registoData }) => (this.registoData = registoData));
  }

  previousState(): void {
    window.history.back();
  }
}
