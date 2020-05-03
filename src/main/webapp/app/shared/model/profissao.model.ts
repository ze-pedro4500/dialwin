import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

export interface IProfissao {
  id?: number;
  nome?: string;
  doenteSocioFamiliars?: IDoenteSocioFamiliar[];
}

export class Profissao implements IProfissao {
  constructor(public id?: number, public nome?: string, public doenteSocioFamiliars?: IDoenteSocioFamiliar[]) {}
}
