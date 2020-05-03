export interface IVitalidade {
  id?: number;
  nome?: string;
  percentagem?: number;
}

export class Vitalidade implements IVitalidade {
  constructor(public id?: number, public nome?: string, public percentagem?: number) {}
}
