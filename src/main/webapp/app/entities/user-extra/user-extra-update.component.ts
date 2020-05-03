import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IUserExtra, UserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from './user-extra.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IUserPermissions } from 'app/shared/model/user-permissions.model';
import { UserPermissionsService } from 'app/entities/user-permissions/user-permissions.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

@Component({
  selector: 'jhi-user-extra-update',
  templateUrl: './user-extra-update.component.html'
})
export class UserExtraUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  userpermissions: IUserPermissions[];

  userprofiles: IUserProfile[];

  editForm = this.fb.group({
    id: [],
    activo: [],
    nome: [],
    morada: [],
    codP: [],
    telef: [],
    permissChange: [],
    nif: [],
    user: [],
    userPermissions: [],
    userProfile: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userExtraService: UserExtraService,
    protected userService: UserService,
    protected userPermissionsService: UserPermissionsService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userExtra }) => {
      this.updateForm(userExtra);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.userPermissionsService.query({ filter: 'userextra-is-null' }).subscribe(
      (res: HttpResponse<IUserPermissions[]>) => {
        if (!this.editForm.get('userPermissions').value || !this.editForm.get('userPermissions').value.id) {
          this.userpermissions = res.body;
        } else {
          this.userPermissionsService
            .find(this.editForm.get('userPermissions').value.id)
            .subscribe(
              (subRes: HttpResponse<IUserPermissions>) => (this.userpermissions = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.userProfileService
      .query()
      .subscribe(
        (res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(userExtra: IUserExtra) {
    this.editForm.patchValue({
      id: userExtra.id,
      activo: userExtra.activo,
      nome: userExtra.nome,
      morada: userExtra.morada,
      codP: userExtra.codP,
      telef: userExtra.telef,
      permissChange: userExtra.permissChange,
      nif: userExtra.nif,
      user: userExtra.user,
      userPermissions: userExtra.userPermissions,
      userProfile: userExtra.userProfile
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userExtra = this.createFromForm();
    if (userExtra.id !== undefined) {
      this.subscribeToSaveResponse(this.userExtraService.update(userExtra));
    } else {
      this.subscribeToSaveResponse(this.userExtraService.create(userExtra));
    }
  }

  private createFromForm(): IUserExtra {
    return {
      ...new UserExtra(),
      id: this.editForm.get(['id']).value,
      activo: this.editForm.get(['activo']).value,
      nome: this.editForm.get(['nome']).value,
      morada: this.editForm.get(['morada']).value,
      codP: this.editForm.get(['codP']).value,
      telef: this.editForm.get(['telef']).value,
      permissChange: this.editForm.get(['permissChange']).value,
      nif: this.editForm.get(['nif']).value,
      user: this.editForm.get(['user']).value,
      userPermissions: this.editForm.get(['userPermissions']).value,
      userProfile: this.editForm.get(['userProfile']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserExtra>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackUserPermissionsById(index: number, item: IUserPermissions) {
    return item.id;
  }

  trackUserProfileById(index: number, item: IUserProfile) {
    return item.id;
  }
}
