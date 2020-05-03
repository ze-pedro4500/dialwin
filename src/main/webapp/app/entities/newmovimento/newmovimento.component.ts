import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDoenteHistMovimentos, DoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';
import { DoenteHistMovimentosService } from '../doente-hist-movimentos/doente-hist-movimentos.service';
import { IDoente } from 'app/shared/model/doente.model';
import { DoenteService } from 'app/entities/doente/doente.service';
import { DataService } from 'app/data.service';

@Component({
  selector: 'jhi-newmovimento',
  templateUrl: './newmovimento.component.html',
  styleUrls: ['./newmovimento.component.scss']
})
export class NewmovimentoComponent implements OnInit {

  isSaving: boolean;

  doentes: IDoente[];
  dataDp: any;
  doenteId:number;
  doente:IDoente;

  
  editForm = this.fb.group({
    id: [],
    data: [],
    situacao: [],
    obs: [],
    doente: []
  });


  constructor(protected jhiAlertService: JhiAlertService,
    protected doenteHistMovimentosService: DoenteHistMovimentosService,
    protected doenteService: DoenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private data: DataService) { }

  ngOnInit() {
    this.isSaving = false;
    this.data.currentDoente.subscribe((dti) =>{
      this.doenteId=dti;
      this.doenteService.find(this.doenteId).subscribe((dt) =>{
        this.doente=dt.body;
      })
    })
    this.doenteService
      .query()
      .subscribe((res: HttpResponse<IDoente[]>) => (this.doentes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }



  save() {
    this.isSaving = true;
    const doenteHistMovimentos = this.createFromForm();
      this.subscribeToSaveResponse(this.doenteHistMovimentosService.create(doenteHistMovimentos));
  }

  private createFromForm(): IDoenteHistMovimentos {
    return {
      ...new DoenteHistMovimentos(),
      data: this.editForm.get(['data']).value,
      situacao: this.editForm.get(['situacao']).value,
      obs: this.editForm.get(['obs']).value,
      doente: this.doente
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteHistMovimentos>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDoenteById(index: number, item: IDoente) {
    return item.id;
  }

}
