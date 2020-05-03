import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';

@Component({
  selector: 'jhi-doente-diagnostico-social-detail',
  templateUrl: './doente-diagnostico-social-detail.component.html'
})
export class DoenteDiagnosticoSocialDetailComponent implements OnInit {
  doenteDiagnosticoSocial: IDoenteDiagnosticoSocial;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doenteDiagnosticoSocial }) => {
      this.doenteDiagnosticoSocial = doenteDiagnosticoSocial;
    });
  }

  previousState() {
    window.history.back();
  }
}
