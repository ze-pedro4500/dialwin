import { Moment } from 'moment';

export interface IRegistoData {
  id?: number;
  registo?: number;
  data?: Moment;
}

export class RegistoData implements IRegistoData {
  constructor(public id?: number, public registo?: number, public data?: Moment) {}
}
