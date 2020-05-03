import { IUserExtra } from 'app/shared/model/user-extra.model';

export interface IUserPermissions {
  id?: number;
  demograf?: number;
  social?: number;
  procClin?: number;
  dialEnf?: number;
  dialStat?: number;
  qualiStat?: number;
  labLink?: number;
  gidLink?: number;
  asterixFarma?: number;
  asterixGabMed?: number;
  userExtra?: IUserExtra;
}

export class UserPermissions implements IUserPermissions {
  constructor(
    public id?: number,
    public demograf?: number,
    public social?: number,
    public procClin?: number,
    public dialEnf?: number,
    public dialStat?: number,
    public qualiStat?: number,
    public labLink?: number,
    public gidLink?: number,
    public asterixFarma?: number,
    public asterixGabMed?: number,
    public userExtra?: IUserExtra
  ) {}
}
