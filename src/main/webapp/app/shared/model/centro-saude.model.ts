import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';

export interface ICentroSaude {
  id?: number;
  nome?: string;
  doenteIdentidades?: IDoenteIdentidade[];
}

export class CentroSaude implements ICentroSaude {
  constructor(public id?: number, public nome?: string, public doenteIdentidades?: IDoenteIdentidade[]) {}
}
