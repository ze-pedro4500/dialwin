import { Component, OnInit } from '@angular/core';
import { ISubSistemas } from '../../shared/model/sub-sistemas.model';
import { SubSistemasService } from '../sub-sistemas/sub-sistemas.service';
import { HttpResponse } from '@angular/common/http';
import { ITurnos } from '../../shared/model/turnos.model';
import { TurnosService } from '../../entities/turnos/turnos.service';
import { IDoente } from '../../shared/model/doente.model';
import { DoenteService } from '../doente/doente.service';
import { IDoenteIdentidade } from '../../shared/model/doente-identidade.model';
import { DoenteIdentidadeService } from '../doente-identidade/doente-identidade.service';
import { DataService } from '../../data.service'
@Component({
  selector: 'jhi-seletordoente',
  templateUrl: './seletordoente.component.html',
  styleUrls: ['./seletordoente.component.scss']
})
export class SeletordoenteComponent implements OnInit {
  subSistemas: ISubSistemas[];
  turnos: ITurnos[];
  doentes: IDoente[];
  doenteIdentidades: IDoenteIdentidade[] = [];
  situacao ="" ;
  sit= "";
  sub ="";
  turno ="";
  doenteId:number;
  selectDoente:boolean;
  doenteNome: string;
  d= new Date();
  diaSemana: string;
  hora:string;
  turnoId:number;
// PASSAR SUBSISTEMA e turno DE NOME PARA ID

  constructor(
    private data: DataService,
    protected subSistemasService: SubSistemasService,
    protected turnosService: TurnosService,
    protected doenteService: DoenteService,
    protected doenteIdentidadeService: DoenteIdentidadeService
  ) { }

  loadAllSub() {
    this.subSistemasService.query().subscribe((res: HttpResponse<ISubSistemas[]>) => {
      this.subSistemas = res.body;
    });
  }

  loadAllTu() {
    this.turnosService.query().subscribe((res: HttpResponse<ITurnos[]>) => {
      this.turnos = res.body;
    });
  }

  loadAllDo() {
    this.doenteService.query().subscribe((res: HttpResponse<IDoente[]>) => {
      this.doentes = res.body;
      this.loadAllDoId();
    });
  }

  loadDo(sit: any, sub: any, turno: any) {
    this.getTurnoAtual();
  if(turno==="Turno atual"){
    turno= this.turnoId
  }
   this.turnos.forEach(t => {
     if(turno === t.nome){
       turno = t.id;
     }
   })
   this.subSistemas.forEach(subSistema => {
      if(subSistema.gidNome === sub){
        sub = subSistema.id;
      }
    });
    this.doenteService.search(sit, sub, turno).subscribe((res: HttpResponse<IDoente[] >) => {
      this.doentes = res.body;
      this.loadAllDoId();
    })
  }

  loadAllDoId () {
    this.doenteIdentidades=[];
    this.doentes.forEach(doente => {
      this.doenteIdentidadeService.search(doente.id).subscribe((resp: HttpResponse<IDoenteIdentidade>) => {
        this.doenteIdentidades.push(resp.body);
      });
    });
  }

  ngOnInit() {
    this.data.currenteDoenteNome.subscribe(doenteNome => this.doenteNome = doenteNome)
    this.data.currentSelect.subscribe(selectDoente => this.selectDoente = selectDoente)
    this.data.currentDoente.subscribe(doenteId => this.doenteId = doenteId)
    if(this.doenteId !==0){
      this.selectDoente=false;
    }
    this.loadAllSub();
    this.loadAllTu();
    this.loadAllDo();
  }

  getTurnoAtual(){
    const v= this.d.getDay();
    const h = this.d.getHours();
    if(v===1 || v===3 || v===5){
      if(h<12){
        this.turnoId=1;
      }
      if(h>12 && h<18){
        this.turnoId=2;
      }
      if(h>18){
        this.turnoId=3;
      }
    }
    if(v===2 || v===4 || v===6){
      if(h<12){
        this.turnoId=4;
      }
      if(h>=12 && h<18){
        this.turnoId=5;
      }
      if(h>=18){
        this.turnoId=6;
      }
    }
  }

  changeSelect(select:boolean){
    this.data.changeSelect;
    
  }

  newDoente(){
    this.data.changeDoente(3)
  }

  logar(did: IDoenteIdentidade){
    this.data.changeIdentidade(true);
    this.data.changeDoente(did.doente.id)
    this.data.changeDoenteNome(did.nome);
    this.selectDoente=false
  }

}
