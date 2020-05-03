import { IDoente } from 'app/shared/model/doente.model';

export interface IDoenteContactos {
  id?: number;
  transportador?: string;
  telefTransp?: number;
  doente?: IDoente;
}

export class DoenteContactos implements IDoenteContactos {
  constructor(public id?: number, public transportador?: string, public telefTransp?: number, public doente?: IDoente) {}
}
