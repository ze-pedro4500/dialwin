import { IDoente } from 'app/shared/model/doente.model';

export interface IDoenteRegistosIntervencoes {
  id?: number;
  descr?: string;
  doente?: IDoente;
}

export class DoenteRegistosIntervencoes implements IDoenteRegistosIntervencoes {
  constructor(public id?: number, public descr?: string, public doente?: IDoente) {}
}
