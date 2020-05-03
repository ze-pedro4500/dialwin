import { IDoente } from 'app/shared/model/doente.model';

export interface IHorarioDoente {
  id?: number;
  dia?: string;
  turno?: string;
  sala?: number;
  posto?: string;
  duracao?: number;
  doente?: IDoente;
}

export class HorarioDoente implements IHorarioDoente {
  constructor(
    public id?: number,
    public dia?: string,
    public turno?: string,
    public sala?: number,
    public posto?: string,
    public duracao?: number,
    public doente?: IDoente
  ) {}
}
