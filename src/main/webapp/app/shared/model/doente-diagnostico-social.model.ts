import { IDoente } from 'app/shared/model/doente.model';

export interface IDoenteDiagnosticoSocial {
  id?: number;
  descr?: string;
  doente?: IDoente;
}

export class DoenteDiagnosticoSocial implements IDoenteDiagnosticoSocial {
  constructor(public id?: number, public descr?: string, public doente?: IDoente) {}
}
