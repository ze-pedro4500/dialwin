import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class DataService {

  private doenteSource = new BehaviorSubject(0);
  currentDoente = this.doenteSource.asObservable();

  private selectSource = new BehaviorSubject(true);
  currentSelect = this.selectSource.asObservable();

  private doenteNomeSource = new BehaviorSubject("Selecionar Doente")
  currenteDoenteNome = this.doenteNomeSource.asObservable();

  private identidadeSource = new BehaviorSubject(false);
  currentIdentidade = this.identidadeSource.asObservable();

  private contactosSource = new BehaviorSubject(false);
  currentContactos = this.contactosSource.asObservable();

  private socioFamiliarSource = new BehaviorSubject(false);
  currentSocioFamiliar = this.socioFamiliarSource.asObservable();

  private perfilSocialSource = new BehaviorSubject(false);
  currentPerfilSocial = this.perfilSocialSource.asObservable();

  private horarioSource = new BehaviorSubject(false);
  currentHorario = this.horarioSource.asObservable();

  private historicoSource = new BehaviorSubject(false);
  currentHistorico = this.historicoSource.asObservable();


  private newcontactoSource = new BehaviorSubject(false);
  currentnewcontacto = this.newcontactoSource.asObservable();

 
  constructor() { }

  changenewcontacto(nc:boolean){
    this.newcontactoSource.next(nc);
  }


  changeHistorico(hi:boolean){
    this.historicoSource.next(hi);
  }

  changeHorario(h:boolean){
    this.horarioSource.next(h);
  }

  changePerfilSocial(ps:boolean){
    this.perfilSocialSource.next(ps);
  }

  changeSocioFamiliar(sf:boolean){
    this.socioFamiliarSource.next(sf);
  }

  changeContactos(ct:boolean){
    this.contactosSource.next(ct);
  }

  changeIdentidade(id:boolean){
    this.identidadeSource.next(id);
  }

  changeSelect(select: boolean){
    this.selectSource.next(select)
  }
 
  changeDoente(doenteId: number) {
    this.doenteSource.next(doenteId)
  }

  changeDoenteNome(doenteNome: string){
    this.doenteNomeSource.next(doenteNome)
  }


}
