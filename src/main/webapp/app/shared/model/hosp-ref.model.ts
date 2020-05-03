import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';

export interface IHospRef {
  id?: number;
  nome?: string;
  doenteIdentidades?: IDoenteIdentidade[];
}

export class HospRef implements IHospRef {
  constructor(public id?: number, public nome?: string, public doenteIdentidades?: IDoenteIdentidade[]) {}
}
