import { IDoente } from 'app/shared/model/doente.model';

export interface ITurnos {
  id?: number;
  nome?: string;
  doentes?: IDoente[];
}

export class Turnos implements ITurnos {
  constructor(public id?: number, public nome?: string, public doentes?: IDoente[]) {}
}
