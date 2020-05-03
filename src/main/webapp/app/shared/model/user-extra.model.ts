import { IUser } from 'app/core/user/user.model';
import { IUserPermissions } from 'app/shared/model/user-permissions.model';
import { IUserProfile } from 'app/shared/model/user-profile.model';

export interface IUserExtra {
  id?: number;
  activo?: boolean;
  nome?: string;
  morada?: string;
  codP?: string;
  telef?: string;
  permissChange?: boolean;
  nif?: number;
  user?: IUser;
  userPermissions?: IUserPermissions;
  userProfile?: IUserProfile;
}

export class UserExtra implements IUserExtra {
  constructor(
    public id?: number,
    public activo?: boolean,
    public nome?: string,
    public morada?: string,
    public codP?: string,
    public telef?: string,
    public permissChange?: boolean,
    public nif?: number,
    public user?: IUser,
    public userPermissions?: IUserPermissions,
    public userProfile?: IUserProfile
  ) {
    this.activo = this.activo || false;
    this.permissChange = this.permissChange || false;
  }
}
