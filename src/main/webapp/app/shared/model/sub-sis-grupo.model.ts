export interface ISubSisGrupo {
  id?: number;
  gidDesigna?: string;
  gidGrupo?: string;
}

export class SubSisGrupo implements ISubSisGrupo {
  constructor(public id?: number, public gidDesigna?: string, public gidGrupo?: string) {}
}
