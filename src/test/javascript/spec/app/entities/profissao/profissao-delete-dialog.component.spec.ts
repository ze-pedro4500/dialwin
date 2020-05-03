import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { ProfissaoDeleteDialogComponent } from 'app/entities/profissao/profissao-delete-dialog.component';
import { ProfissaoService } from 'app/entities/profissao/profissao.service';

describe('Component Tests', () => {
  describe('Profissao Management Delete Component', () => {
    let comp: ProfissaoDeleteDialogComponent;
    let fixture: ComponentFixture<ProfissaoDeleteDialogComponent>;
    let service: ProfissaoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [ProfissaoDeleteDialogComponent]
      })
        .overrideTemplate(ProfissaoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfissaoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfissaoService);
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
