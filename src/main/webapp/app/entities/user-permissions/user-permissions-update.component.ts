import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IUserPermissions, UserPermissions } from 'app/shared/model/user-permissions.model';
import { UserPermissionsService } from './user-permissions.service';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/user-extra.service';

@Component({
  selector: 'jhi-user-permissions-update',
  templateUrl: './user-permissions-update.component.html'
})
export class UserPermissionsUpdateComponent implements OnInit {
  isSaving: boolean;

  userextras: IUserExtra[];

  editForm = this.fb.group({
    id: [],
    demograf: [],
    social: [],
    procClin: [],
    dialEnf: [],
    dialStat: [],
    qualiStat: [],
    labLink: [],
    gidLink: [],
    asterixFarma: [],
    asterixGabMed: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userPermissionsService: UserPermissionsService,
    protected userExtraService: UserExtraService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userPermissions }) => {
      this.updateForm(userPermissions);
    });
    this.userExtraService
      .query()
      .subscribe((res: HttpResponse<IUserExtra[]>) => (this.userextras = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(userPermissions: IUserPermissions) {
    this.editForm.patchValue({
      id: userPermissions.id,
      demograf: userPermissions.demograf,
      social: userPermissions.social,
      procClin: userPermissions.procClin,
      dialEnf: userPermissions.dialEnf,
      dialStat: userPermissions.dialStat,
      qualiStat: userPermissions.qualiStat,
      labLink: userPermissions.labLink,
      gidLink: userPermissions.gidLink,
      asterixFarma: userPermissions.asterixFarma,
      asterixGabMed: userPermissions.asterixGabMed
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userPermissions = this.createFromForm();
    if (userPermissions.id !== undefined) {
      this.subscribeToSaveResponse(this.userPermissionsService.update(userPermissions));
    } else {
      this.subscribeToSaveResponse(this.userPermissionsService.create(userPermissions));
    }
  }

  private createFromForm(): IUserPermissions {
    return {
      ...new UserPermissions(),
      id: this.editForm.get(['id']).value,
      demograf: this.editForm.get(['demograf']).value,
      social: this.editForm.get(['social']).value,
      procClin: this.editForm.get(['procClin']).value,
      dialEnf: this.editForm.get(['dialEnf']).value,
      dialStat: this.editForm.get(['dialStat']).value,
      qualiStat: this.editForm.get(['qualiStat']).value,
      labLink: this.editForm.get(['labLink']).value,
      gidLink: this.editForm.get(['gidLink']).value,
      asterixFarma: this.editForm.get(['asterixFarma']).value,
      asterixGabMed: this.editForm.get(['asterixGabMed']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserPermissions>>) {
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

  trackUserExtraById(index: number, item: IUserExtra) {
    return item.id;
  }
}
