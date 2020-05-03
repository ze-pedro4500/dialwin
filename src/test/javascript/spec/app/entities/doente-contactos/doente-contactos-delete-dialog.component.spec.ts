import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { DoenteContactosDeleteDialogComponent } from 'app/entities/doente-contactos/doente-contactos-delete-dialog.component';
import { DoenteContactosService } from 'app/entities/doente-contactos/doente-contactos.service';

describe('Component Tests', () => {
  describe('DoenteContactos Management Delete Component', () => {
    let comp: DoenteContactosDeleteDialogComponent;
    let fixture: ComponentFixture<DoenteContactosDeleteDialogComponent>;
    let service: DoenteContactosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteContactosDeleteDialogComponent]
      })
        .overrideTemplate(DoenteContactosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteContactosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteContactosService);
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
