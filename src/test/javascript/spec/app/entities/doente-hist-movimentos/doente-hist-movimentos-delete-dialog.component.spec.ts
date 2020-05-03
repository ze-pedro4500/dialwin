import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { DoenteHistMovimentosDeleteDialogComponent } from 'app/entities/doente-hist-movimentos/doente-hist-movimentos-delete-dialog.component';
import { DoenteHistMovimentosService } from 'app/entities/doente-hist-movimentos/doente-hist-movimentos.service';

describe('Component Tests', () => {
  describe('DoenteHistMovimentos Management Delete Component', () => {
    let comp: DoenteHistMovimentosDeleteDialogComponent;
    let fixture: ComponentFixture<DoenteHistMovimentosDeleteDialogComponent>;
    let service: DoenteHistMovimentosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteHistMovimentosDeleteDialogComponent]
      })
        .overrideTemplate(DoenteHistMovimentosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteHistMovimentosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteHistMovimentosService);
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
