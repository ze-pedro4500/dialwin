import { Component, OnInit, OnDestroy } from '@angular/core';
import { DataService } from '../../data.service'
import { DoenteIdentidadeService } from '../doente-identidade/doente-identidade.service' 

@Component({
  selector: 'jhi-demografia',
  templateUrl: './demografia.component.html',
  styleUrls: ['./demografia.component.scss']
})
export class DemografiaComponent implements OnInit, OnDestroy {

  doenteId: number;
  doenteNome: string;
  selectDoente: boolean;
  identidade: boolean;
  contactos:boolean;
  pfs:boolean;
  horario:boolean;
  movimentos:boolean;
  sociofamiliar:boolean;
  colorA = "blue";
  colorB= "#007bff";
  constructor(private data: DataService,
    protected doIdSer: DoenteIdentidadeService) { 
  }

  ngOnDestroy(){
    this.doenteId=0;
    this.data.changeIdentidade(false);
    this.data.changeContactos(false);
    this.data.changeSocioFamiliar(false);
    this.data.changePerfilSocial(false);
    this.data.changeHorario(false);
    this.data.changeHistorico(false);
  }

  changeSelect(select:boolean){
    this.doenteId=0;
    this.data.changeSelect(select);
    this.data.changeIdentidade(false);
    this.data.changeContactos(false);
    this.data.changeSocioFamiliar(false);
    this.data.changePerfilSocial(false);
    this.data.changeHorario(false);
    this.data.changeHistorico(false);
  }

  identidadeRoute(){
    if(this.doenteId !==0){
    this.data.changeIdentidade(true);
    this.data.changeContactos(false);
    this.data.changeSocioFamiliar(false);
    this.data.changePerfilSocial(false);
    this.data.changeHorario(false);
    this.data.changeHistorico(false);
    }
  }

  contactosRoute(){
    if(this.doenteId !==0){
    this.data.changeIdentidade(false);
    this.data.changeContactos(true);
    this.data.changeSocioFamiliar(false);
    this.data.changePerfilSocial(false);
    this.data.changeHorario(false);
    this.data.changeHistorico(false);
    }
  }

  socioFamiliarRoute(){
    if(this.doenteId !==0){
    this.data.changeIdentidade(false);
    this.data.changeContactos(false);
    this.data.changeSocioFamiliar(true);
    this.data.changePerfilSocial(false);
    this.data.changeHorario(false);
    this.data.changeHistorico(false);
    }
  }

  socialRoute(){
    if(this.doenteId !==0){
    this.data.changeIdentidade(false);
    this.data.changeContactos(false);
    this.data.changeSocioFamiliar(false);
    this.data.changePerfilSocial(true);
    this.data.changeHorario(false);
    this.data.changeHistorico(false);
    }
  }

  horarioRoute(){
    if(this.doenteId !==0){
    this.data.changeIdentidade(false);
    this.data.changeContactos(false);
    this.data.changeSocioFamiliar(false);
    this.data.changePerfilSocial(false);
    this.data.changeHorario(true);
    this.data.changeHistorico(false);
    }
  }

  historicoRoute(){
    if(this.doenteId !==0){
    this.data.changeIdentidade(false);
    this.data.changeContactos(false);
    this.data.changeSocioFamiliar(false);
    this.data.changePerfilSocial(false);
    this.data.changeHorario(false);
    this.data.changeHistorico(true);
    }
  }


  ngOnInit() {
    this.data.currentContactos.subscribe(ct => this.contactos = ct)
    this.data.currentDoente.subscribe(doenteId => this.doenteId = doenteId)
    this.data.currenteDoenteNome.subscribe(doenteNome => this.doenteNome = doenteNome)
    this.data.currentSelect.subscribe(selectDoente => this.selectDoente = selectDoente)
    this.data.currentIdentidade.subscribe(identidade => this.identidade = identidade);
    this.data.currentPerfilSocial.subscribe(pfs => this.pfs = pfs);
    this.data.currentHorario.subscribe(hr => this.horario = hr)
    this.data.currentHistorico.subscribe(mv => this.movimentos= mv)
    this.data.currentSocioFamiliar.subscribe(sf => this.sociofamiliar=sf);
  }

}
