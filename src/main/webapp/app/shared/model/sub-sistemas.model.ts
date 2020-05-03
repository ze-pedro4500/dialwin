import { ISubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';

export interface ISubSistemas {
  id?: number;
  gidNome?: string;
  gidCode?: number;
  subSisGrupo?: ISubSisGrupo;
}

export class SubSistemas implements ISubSistemas {
  constructor(public id?: number, public gidNome?: string, public gidCode?: number, public subSisGrupo?: ISubSisGrupo) {}
}
