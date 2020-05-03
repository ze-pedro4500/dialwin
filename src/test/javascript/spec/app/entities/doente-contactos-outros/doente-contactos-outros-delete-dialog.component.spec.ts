import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { DoenteContactosOutrosDeleteDialogComponent } from 'app/entities/doente-contactos-outros/doente-contactos-outros-delete-dialog.component';
import { DoenteContactosOutrosService } from 'app/entities/doente-contactos-outros/doente-contactos-outros.service';

describe('Component Tests', () => {
  describe('DoenteContactosOutros Management Delete Component', () => {
    let comp: DoenteContactosOutrosDeleteDialogComponent;
    let fixture: ComponentFixture<DoenteContactosOutrosDeleteDialogComponent>;
    let service: DoenteContactosOutrosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteContactosOutrosDeleteDialogComponent]
      })
        .overrideTemplate(DoenteContactosOutrosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteContactosOutrosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteContactosOutrosService);
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
