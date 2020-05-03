import { IDoenteIdentidade } from 'app/shared/model/doente-identidade.model';

export interface IPais {
  id?: number;
  nome?: string;
  sigla?: string;
  doenteIdentidade?: IDoenteIdentidade;
}

export class Pais implements IPais {
  constructor(public id?: number, public nome?: string, public sigla?: string, public doenteIdentidade?: IDoenteIdentidade) {}
}
