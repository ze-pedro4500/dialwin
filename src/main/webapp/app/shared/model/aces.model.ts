import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';

export interface IACES {
  id?: number;
  nome?: string;
  doenteIdentidades?: IDoenteIdentidade[];
}

export class ACES implements IACES {
  constructor(public id?: number, public nome?: string, public doenteIdentidades?: IDoenteIdentidade[]) {}
}
