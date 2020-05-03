import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { SitProfDeleteDialogComponent } from 'app/entities/sit-prof/sit-prof-delete-dialog.component';
import { SitProfService } from 'app/entities/sit-prof/sit-prof.service';

describe('Component Tests', () => {
  describe('SitProf Management Delete Component', () => {
    let comp: SitProfDeleteDialogComponent;
    let fixture: ComponentFixture<SitProfDeleteDialogComponent>;
    let service: SitProfService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SitProfDeleteDialogComponent]
      })
        .overrideTemplate(SitProfDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SitProfDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SitProfService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
