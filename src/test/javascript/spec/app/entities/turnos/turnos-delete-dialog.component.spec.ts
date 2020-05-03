import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { TurnosDeleteDialogComponent } from 'app/entities/turnos/turnos-delete-dialog.component';
import { TurnosService } from 'app/entities/turnos/turnos.service';

describe('Component Tests', () => {
  describe('Turnos Management Delete Component', () => {
    let comp: TurnosDeleteDialogComponent;
    let fixture: ComponentFixture<TurnosDeleteDialogComponent>;
    let service: TurnosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [TurnosDeleteDialogComponent]
      })
        .overrideTemplate(TurnosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TurnosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TurnosService);
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
