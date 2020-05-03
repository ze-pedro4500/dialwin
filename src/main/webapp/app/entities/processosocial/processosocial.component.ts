import { Component, OnInit, OnDestroy } from '@angular/core';
import { IDoente } from 'app/shared/model/doente.model';
import { FormBuilder} from '@angular/forms';
import { DoenteService } from 'app/entities/doente/doente.service';
import { IDoenteDiagnosticoSocial, DoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';
import { JhiAlertService } from 'ng-jhipster';
import { DoenteDiagnosticoSocialService } from '../doente-diagnostico-social/doente-diagnostico-social.service';
import { DataService } from '../../data.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subscription } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { DoenteRegistosIntervencoesService } from '../doente-registos-intervencoes/doente-registos-intervencoes.service';
import { DoenteRegistosIntervencoesDeleteDialogComponent } from '../doente-registos-intervencoes/doente-registos-intervencoes-delete-dialog.component';
import { IDoenteRegistosIntervencoes, DoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';
import { RegistoDataService } from '../../entities/registo-data/registo-data.service';
import { IRegistoData } from 'app/shared/model/registo-data.model';
import { DataRegisto } from '../../../data-registo';
@Component({
  selector: 'jhi-processosocial',
  templateUrl: './processosocial.component.html',
  styleUrls: ['./processosocial.component.scss']
})
export class ProcessosocialComponent implements OnInit, OnDestroy {
  doenteRegistosIntervencoes: IDoenteRegistosIntervencoes[];
  eventSubscriber: Subscription;
  doenteId:number;
  perfilSocial:boolean;

  isSaving: boolean;
  doentes: IDoente[];

  newRegisto=false;
  isSaving2:boolean;
  doente:IDoente;

  dataRegistos = []

  editForm = this.fb.group({
    id: [],
    descr: [],
    doente: []
  });

  editForm2 = this.fb.group({
    id: [],
    descr: [],
    doente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doenteRegistosIntervencoesService: DoenteRegistosIntervencoesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected doenteDiagnosticoSocialService: DoenteDiagnosticoSocialService,
    protected doenteService: DoenteService,
    private fb: FormBuilder,
    private data: DataService,
    protected registoDataService: RegistoDataService
    ) { }

    save2() {
      this.isSaving = true;
      const doenteRegistosIntervencoes = this.createFromForm2();
        this.subscribeToSaveResponse2(this.doenteRegistosIntervencoesService.create(doenteRegistosIntervencoes));
        this.newRegisto=false;
        this.loadAll();
        this.delay(2000);
        this.loadAll();
    }

    async delay(ms: number) {
      await new Promise(resolve => setTimeout(()=>resolve(), ms)).then(()=>console.log("fired"));
  }

    private createFromForm2(): IDoenteRegistosIntervencoes {
      return {
        ...new DoenteRegistosIntervencoes(),
        descr: this.editForm2.get(['descr']).value,
        doente: this.doente
      };
    }

    loadAll() {
      this.doenteRegistosIntervencoesService.search(this.doenteId).subscribe((res: HttpResponse<IDoenteRegistosIntervencoes[]>) => {
        this.doenteRegistosIntervencoes = res.body.reverse();
        res.body.forEach(reg => {
          this.registoDataService.search(reg.id).subscribe((res: HttpResponse<IRegistoData>) =>{
           const dataRegisto: DataRegisto = new DataRegisto();
           dataRegisto.data= res.body;
           dataRegisto.reg=reg;
           this.dataRegistos.push(dataRegisto);
           console.log("HERE");
           console.log(this.dataRegistos);
          })
        });
        
      });
    }

    cancel(){
      this.newRegisto=false;
      this.editForm2.reset();
    }

    addRegisto(){
      this.newRegisto=true;
    }

  ngOnInit() {
    this.isSaving2 = false;
    this.isSaving=false;
    this.data.currentPerfilSocial.subscribe((ps) =>{
      this.perfilSocial = ps;
    })
    this.data.currentDoente.subscribe((doenteId) => {
      this.doenteId=doenteId;
      this.doenteService.find(this.doenteId).subscribe((dt) =>{
        this.doente=dt.body;
      })
      this.loadAll();
    this.registerChangeInDoenteRegistosIntervencoes();
      this.doenteDiagnosticoSocialService.search(this.doenteId).subscribe((res) => {
        this.updateForm(res.body)
      })
    })
    this.doenteService
      .query()
      .subscribe((res: HttpResponse<IDoente[]>) => (this.doentes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  protected subscribeToSaveResponse2(result: Observable<HttpResponse<IDoenteRegistosIntervencoes>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  trackId(index: number, item: IDoenteRegistosIntervencoes) {
    return item.id;
  }

  registerChangeInDoenteRegistosIntervencoes() {
    this.eventSubscriber = this.eventManager.subscribe('doenteRegistosIntervencoesListModification', () => this.loadAll());
  }

  delete(doenteRegistosIntervencoes: IDoenteRegistosIntervencoes) {
    const modalRef = this.modalService.open(DoenteRegistosIntervencoesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.doenteRegistosIntervencoes = doenteRegistosIntervencoes;
  }

  updateForm(doenteDiagnosticoSocial: IDoenteDiagnosticoSocial) {
    this.editForm.patchValue({
      id: doenteDiagnosticoSocial.id,
      descr: doenteDiagnosticoSocial.descr,
      doente: this.doente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenteDiagnosticoSocial = this.createFromForm();
      this.subscribeToSaveResponse(this.doenteDiagnosticoSocialService.create(doenteDiagnosticoSocial));
  }

  private createFromForm(): IDoenteDiagnosticoSocial {
    return {
      ...new DoenteDiagnosticoSocial(),
      id: this.editForm.get(['id']).value,
      descr: this.editForm.get(['descr']).value,
      doente: this.editForm.get(['doente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteDiagnosticoSocial>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
  }

  protected onSaveSuccess2() {
    this.isSaving = false;
  }

  protected onSaveError2() {
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
