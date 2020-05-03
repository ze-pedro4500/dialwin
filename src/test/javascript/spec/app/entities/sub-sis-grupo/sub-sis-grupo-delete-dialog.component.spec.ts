import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { SubSisGrupoDeleteDialogComponent } from 'app/entities/sub-sis-grupo/sub-sis-grupo-delete-dialog.component';
import { SubSisGrupoService } from 'app/entities/sub-sis-grupo/sub-sis-grupo.service';

describe('Component Tests', () => {
  describe('SubSisGrupo Management Delete Component', () => {
    let comp: SubSisGrupoDeleteDialogComponent;
    let fixture: ComponentFixture<SubSisGrupoDeleteDialogComponent>;
    let service: SubSisGrupoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SubSisGrupoDeleteDialogComponent]
      })
        .overrideTemplate(SubSisGrupoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubSisGrupoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubSisGrupoService);
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
