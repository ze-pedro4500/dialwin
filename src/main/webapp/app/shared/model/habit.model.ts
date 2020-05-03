import { IDoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

export interface IHabit {
  id?: number;
  nome?: string;
  doenteSocioFamiliars?: IDoenteSocioFamiliar[];
}

export class Habit implements IHabit {
  constructor(public id?: number, public nome?: string, public doenteSocioFamiliars?: IDoenteSocioFamiliar[]) {}
}
