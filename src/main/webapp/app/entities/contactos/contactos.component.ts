import { Component, OnInit, OnDestroy } from '@angular/core';
import { IDoente } from 'app/shared/model/doente.model';
import { FormBuilder} from '@angular/forms';
import { DataService } from '../../data.service'
import { DoenteContactosService } from '../doente-contactos/doente-contactos.service'
import { IDoenteContactos, DoenteContactos } from '../../shared/model/doente-contactos.model'
import { DoenteService } from 'app/entities/doente/doente.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { IDoenteContactosOutros, DoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';

import { DoenteContactosOutrosService } from '../doente-contactos-outros/doente-contactos-outros.service'
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'jhi-contactos',
  templateUrl: './contactos.component.html',
  styleUrls: ['./contactos.component.scss']
})
export class ContactosComponent implements OnInit, OnDestroy {
  isSaving: boolean;
  doenteId:number;
  doentes: IDoente[];
  contactos:boolean;
  doenteContactosOutros: IDoenteContactosOutros[];
  eventSubscriber: Subscription;
  updatecontactos:boolean;
  newContacto:boolean;

  contactos2:boolean;
  newContacto2:boolean;
  isSaving2: boolean;
  doente:IDoente;
  changedoenteupdate:boolean;

  editForm = this.fb.group({
    id: [],
    transportador: [],
    telefTransp: [],
    doente: []
  });

  editForm2 = this.fb.group({
    id: [],
    nome: [],
    parentesco: [],
    coabita: [],
    telef: [],
    ocupacao: [],
    obs: [],
    preferencial: [],
    doente: []
  });

  constructor(private fb: FormBuilder,
    protected eventManager: JhiEventManager,
    protected doenteContactosOutrosService: DoenteContactosOutrosService,
    protected doenteContactosService: DoenteContactosService,
    protected jhiAlertService: JhiAlertService,
    protected doenteService: DoenteService,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute,
    private data: DataService) { }

  ngOnInit() {
    this.data.currentnewcontacto.subscribe((cnc) => {
      this.newContacto2 = cnc;
    })
    
    this.data.currentDoente.subscribe((doent) => {
      this.doenteId = doent;
      this.doenteService.find(this.doenteId).subscribe((doe) => {
        this.doente = doe.body;
      })
    })
    this.data.currentContactos.subscribe((ct) => {
      this.contactos2= ct;
    });
    this.isSaving2 = false;


    this.data.currentnewcontacto.subscribe(nc =>{
      this.newContacto=nc;
      this.loadAll();
        this.registerChangeInDoenteContactosOutros();
    })
    this.isSaving = false;
    this.data.currentContactos.subscribe((ct) => {
      this.contactos= ct;
    })
    this.data.currentDoente.subscribe((doenteId) => {
      this.doenteId=doenteId;
      this.loadAll();
      this.registerChangeInDoenteContactosOutros();
      this.doenteContactosService.search(this.doenteId).subscribe((resp) => {
        this.updateForm(resp.body[0]);
      });
      this.doenteService.query({ filter: 'doentecontactos-is-null' }).subscribe(
        (res: HttpResponse<IDoente[]>) => {
          if (!this.editForm.get('doente').value || !this.editForm.get('doente').value.id) {
            this.doentes = res.body;
          } else {
            this.doenteService
              .find(this.editForm.get('doente').value.id)
              .subscribe(
                (subRes: HttpResponse<IDoente>) => (this.doentes = [subRes.body].concat(res.body)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    })
  }

  loadAll() {
      this.doenteContactosOutrosService.search(this.doenteId).subscribe((res: HttpResponse<IDoenteContactosOutros[]>) => {
        this.doenteContactosOutros = res.body;
    })
}


save2() {
  this.isSaving2 = true;
  const doenteContactosOutros = this.createFromForm2();
    this.subscribeToSaveResponse2(this.doenteContactosOutrosService.create(doenteContactosOutros));
    this.data.changenewcontacto(false);
    this.loadAll();
    this.loadAll();
    this.loadAll();
    this.delay(2000);
    this.loadAll();
}

cancel(){
  this.newContacto=false;
  this.editForm2.reset();
}

async delay(ms: number) {
  await new Promise(resolve => setTimeout(()=>resolve(), ms)).then(()=>console.log("fired"));
}

private createFromForm2(): IDoenteContactosOutros {
  return {
    ...new DoenteContactosOutros(),
    id: this.editForm2.get(['id']).value,
    nome: this.editForm2.get(['nome']).value,
    parentesco: this.editForm2.get(['parentesco']).value,
    coabita: this.editForm2.get(['coabita']).value,
    telef: this.editForm2.get(['telef']).value,
    ocupacao: this.editForm2.get(['ocupacao']).value,
    obs: this.editForm2.get(['obs']).value,
    preferencial: this.editForm2.get(['preferencial']).value,
    doente: this.doente
  };
}

protected subscribeToSaveResponse2(result: Observable<HttpResponse<IDoenteContactosOutros>>) {
  result.subscribe(() => this.onSaveSuccess2(), () => this.onSaveError2());
}

protected onSaveSuccess2() {
  this.isSaving2 = false;
}

protected onSaveError2() {
  this.isSaving2 = false;
}


ngOnDestroy() {
  this.eventManager.destroy(this.eventSubscriber);
}

trackId(index: number, item: IDoenteContactosOutros) {
  return item.id;
}

registerChangeInDoenteContactosOutros() {
  this.eventSubscriber = this.eventManager.subscribe('doenteContactosOutrosListModification', () => this.loadAll());
}

delete(doenteContactosOutros: IDoenteContactosOutros) {
  this.doenteContactosOutrosService.delete(doenteContactosOutros.id).subscribe(() => {
    this.eventManager.broadcast({
      name: 'doenteContactosOutrosListModification',
      content: 'Deleted an doenteContactosOutros'
    });
  this.loadAll();
  });
}

  updateForm(doenteContactos: IDoenteContactos) {
    this.editForm.patchValue({
      id: doenteContactos.id,
      transportador: doenteContactos.transportador,
      telefTransp: doenteContactos.telefTransp,
      doente: doenteContactos.doente
    });
  }

  private createFromForm(): IDoenteContactos {
    return {
      ...new DoenteContactos(),
      id: this.editForm.get(['id']).value,
      transportador: this.editForm.get(['transportador']).value,
      telefTransp: this.editForm.get(['telefTransp']).value,
      doente: this.editForm.get(['doente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenteContactos>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  addContacto(){
    this.newContacto=true;
    this.data.changenewcontacto(true);
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenteContactos = this.createFromForm();
    if (doenteContactos.id !== undefined) {
      this.subscribeToSaveResponse(this.doenteContactosService.update(doenteContactos));
    } else {
      this.subscribeToSaveResponse(this.doenteContactosService.create(doenteContactos));
    }
  }

  trackDoenteById(index: number, item: IDoente) {
    return item.id;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

}
