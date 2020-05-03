import { IDoente } from 'app/shared/model/doente.model';

export interface IDoenteContactosOutros {
  id?: number;
  nome?: string;
  parentesco?: string;
  coabita?: boolean;
  telef?: number;
  ocupacao?: string;
  obs?: string;
  preferencial?: boolean;
  doente?: IDoente;
}

export class DoenteContactosOutros implements IDoenteContactosOutros {
  constructor(
    public id?: number,
    public nome?: string,
    public parentesco?: string,
    public coabita?: boolean,
    public telef?: number,
    public ocupacao?: string,
    public obs?: string,
    public preferencial?: boolean,
    public doente?: IDoente
  ) {
    this.coabita = this.coabita || false;
    this.preferencial = this.preferencial || false;
  }
}
