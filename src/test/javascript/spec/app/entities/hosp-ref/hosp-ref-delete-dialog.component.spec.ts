import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { HospRefDeleteDialogComponent } from 'app/entities/hosp-ref/hosp-ref-delete-dialog.component';
import { HospRefService } from 'app/entities/hosp-ref/hosp-ref.service';

describe('Component Tests', () => {
  describe('HospRef Management Delete Component', () => {
    let comp: HospRefDeleteDialogComponent;
    let fixture: ComponentFixture<HospRefDeleteDialogComponent>;
    let service: HospRefService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [HospRefDeleteDialogComponent]
      })
        .overrideTemplate(HospRefDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HospRefDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HospRefService);
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
