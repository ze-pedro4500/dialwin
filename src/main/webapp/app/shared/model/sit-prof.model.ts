import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

export interface ISitProf {
  id?: number;
  nome?: string;
  doenteSocioFamiliars?: IDoenteSocioFamiliar[];
}

export class SitProf implements ISitProf {
  constructor(public id?: number, public nome?: string, public doenteSocioFamiliars?: IDoenteSocioFamiliar[]) {}
}
