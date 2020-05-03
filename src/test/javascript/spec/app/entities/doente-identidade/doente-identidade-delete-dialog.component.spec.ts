import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { DoenteIdentidadeDeleteDialogComponent } from 'app/entities/doente-identidade/doente-identidade-delete-dialog.component';
import { DoenteIdentidadeService } from 'app/entities/doente-identidade/doente-identidade.service';

describe('Component Tests', () => {
  describe('DoenteIdentidade Management Delete Component', () => {
    let comp: DoenteIdentidadeDeleteDialogComponent;
    let fixture: ComponentFixture<DoenteIdentidadeDeleteDialogComponent>;
    let service: DoenteIdentidadeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteIdentidadeDeleteDialogComponent]
      })
        .overrideTemplate(DoenteIdentidadeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteIdentidadeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteIdentidadeService);
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
