import { Moment } from 'moment';
import { IDoente } from 'app/shared/model/doente.model';

export interface IDoenteHistMovimentos {
  id?: number;
  data?: Moment;
  situacao?: string;
  obs?: string;
  doente?: IDoente;
}

export class DoenteHistMovimentos implements IDoenteHistMovimentos {
  constructor(public id?: number, public data?: Moment, public situacao?: string, public obs?: string, public doente?: IDoente) {}
}
