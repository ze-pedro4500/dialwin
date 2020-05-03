import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { UserProfileDeleteDialogComponent } from './user-profile-delete-dialog.component';

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html'
})
export class UserProfileComponent implements OnInit, OnDestroy {
  userProfiles: IUserProfile[];
  eventSubscriber: Subscription;

  constructor(
    protected userProfileService: UserProfileService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => {
      this.userProfiles = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInUserProfiles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserProfile) {
    return item.id;
  }

  registerChangeInUserProfiles() {
    this.eventSubscriber = this.eventManager.subscribe('userProfileListModification', () => this.loadAll());
  }

  delete(userProfile: IUserProfile) {
    const modalRef = this.modalService.open(UserProfileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userProfile = userProfile;
  }
}
