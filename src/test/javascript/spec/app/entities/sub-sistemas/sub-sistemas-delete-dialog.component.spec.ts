import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { SubSistemasDeleteDialogComponent } from 'app/entities/sub-sistemas/sub-sistemas-delete-dialog.component';
import { SubSistemasService } from 'app/entities/sub-sistemas/sub-sistemas.service';

describe('Component Tests', () => {
  describe('SubSistemas Management Delete Component', () => {
    let comp: SubSistemasDeleteDialogComponent;
    let fixture: ComponentFixture<SubSistemasDeleteDialogComponent>;
    let service: SubSistemasService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SubSistemasDeleteDialogComponent]
      })
        .overrideTemplate(SubSistemasDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubSistemasDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubSistemasService);
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
