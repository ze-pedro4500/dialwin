import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';

@Component({
  selector: 'jhi-user-profile-update',
  templateUrl: './user-profile-update.component.html'
})
export class UserProfileUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [],
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

  constructor(protected userProfileService: UserProfileService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      this.updateForm(userProfile);
    });
  }

  updateForm(userProfile: IUserProfile) {
    this.editForm.patchValue({
      id: userProfile.id,
      nome: userProfile.nome,
      demograf: userProfile.demograf,
      social: userProfile.social,
      procClin: userProfile.procClin,
      dialEnf: userProfile.dialEnf,
      dialStat: userProfile.dialStat,
      qualiStat: userProfile.qualiStat,
      labLink: userProfile.labLink,
      gidLink: userProfile.gidLink,
      asterixFarma: userProfile.asterixFarma,
      asterixGabMed: userProfile.asterixGabMed
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userProfile = this.createFromForm();
    if (userProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.userProfileService.update(userProfile));
    } else {
      this.subscribeToSaveResponse(this.userProfileService.create(userProfile));
    }
  }

  private createFromForm(): IUserProfile {
    return {
      ...new UserProfile(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfile>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
