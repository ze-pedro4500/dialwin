import { IUserExtra } from 'app/shared/model/user-extra.model';

export interface IUserProfile {
  id?: number;
  nome?: string;
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
  userExtras?: IUserExtra[];
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public nome?: string,
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
    public userExtras?: IUserExtra[]
  ) {}
}
