import { IDoente } from 'app/shared/model/doente.model';
import { IVitalidade } from 'app/shared/model/vitalidade.model';
import { IHabit } from 'app/shared/model/habit.model';
import { IGrauConf } from 'app/shared/model/grau-conf.model';
import { IProfissao } from 'app/shared/model/profissao.model';
import { ISitProf } from 'app/shared/model/sit-prof.model';
import { Habilitacoes } from 'app/shared/model/enumerations/habilitacoes.model';
import { EstCivil } from 'app/shared/model/enumerations/est-civil.model';

export interface IDoenteSocioFamiliar {
  id?: number;
  habilitacoes?: Habilitacoes;
  estCivil?: EstCivil;
  conjugeNome?: string;
  conjugeProf?: string;
  activTempLiv?: string;
  habitacaoObs?: string;
  grauConfortoObs?: string;
  respostaSocial?: string;
  doente?: IDoente;
  vitalidade?: IVitalidade;
  habit?: IHabit;
  grauConf?: IGrauConf;
  profissao?: IProfissao;
  sitProf?: ISitProf;
}

export class DoenteSocioFamiliar implements IDoenteSocioFamiliar {
  constructor(
    public id?: number,
    public habilitacoes?: Habilitacoes,
    public estCivil?: EstCivil,
    public conjugeNome?: string,
    public conjugeProf?: string,
    public activTempLiv?: string,
    public habitacaoObs?: string,
    public grauConfortoObs?: string,
    public respostaSocial?: string,
    public doente?: IDoente,
    public vitalidade?: IVitalidade,
    public habit?: IHabit,
    public grauConf?: IGrauConf,
    public profissao?: IProfissao,
    public sitProf?: ISitProf
  ) {}
}
