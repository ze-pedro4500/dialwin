import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { HorarioDoenteDeleteDialogComponent } from 'app/entities/horario-doente/horario-doente-delete-dialog.component';
import { HorarioDoenteService } from 'app/entities/horario-doente/horario-doente.service';

describe('Component Tests', () => {
  describe('HorarioDoente Management Delete Component', () => {
    let comp: HorarioDoenteDeleteDialogComponent;
    let fixture: ComponentFixture<HorarioDoenteDeleteDialogComponent>;
    let service: HorarioDoenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [HorarioDoenteDeleteDialogComponent]
      })
        .overrideTemplate(HorarioDoenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HorarioDoenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HorarioDoenteService);
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
