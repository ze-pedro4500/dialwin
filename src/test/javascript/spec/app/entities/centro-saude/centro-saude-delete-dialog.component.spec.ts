import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { CentroSaudeDeleteDialogComponent } from 'app/entities/centro-saude/centro-saude-delete-dialog.component';
import { CentroSaudeService } from 'app/entities/centro-saude/centro-saude.service';

describe('Component Tests', () => {
  describe('CentroSaude Management Delete Component', () => {
    let comp: CentroSaudeDeleteDialogComponent;
    let fixture: ComponentFixture<CentroSaudeDeleteDialogComponent>;
    let service: CentroSaudeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [CentroSaudeDeleteDialogComponent]
      })
        .overrideTemplate(CentroSaudeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CentroSaudeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CentroSaudeService);
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
