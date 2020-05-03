import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { UserPermissionsComponent } from 'app/entities/user-permissions/user-permissions.component';
import { UserPermissionsService } from 'app/entities/user-permissions/user-permissions.service';
import { UserPermissions } from 'app/shared/model/user-permissions.model';

describe('Component Tests', () => {
  describe('UserPermissions Management Component', () => {
    let comp: UserPermissionsComponent;
    let fixture: ComponentFixture<UserPermissionsComponent>;
    let service: UserPermissionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [UserPermissionsComponent],
        providers: []
      })
        .overrideTemplate(UserPermissionsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserPermissionsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserPermissionsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserPermissions(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userPermissions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
