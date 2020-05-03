import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

export interface IGrauConf {
  id?: number;
  nome?: string;
  doenteSocioFamiliars?: IDoenteSocioFamiliar[];
}

export class GrauConf implements IGrauConf {
  constructor(public id?: number, public nome?: string, public doenteSocioFamiliars?: IDoenteSocioFamiliar[]) {}
}
