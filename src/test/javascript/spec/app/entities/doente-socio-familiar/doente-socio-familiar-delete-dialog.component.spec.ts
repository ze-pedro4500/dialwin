import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { DoenteSocioFamiliarDeleteDialogComponent } from 'app/entities/doente-socio-familiar/doente-socio-familiar-delete-dialog.component';
import { DoenteSocioFamiliarService } from 'app/entities/doente-socio-familiar/doente-socio-familiar.service';

describe('Component Tests', () => {
  describe('DoenteSocioFamiliar Management Delete Component', () => {
    let comp: DoenteSocioFamiliarDeleteDialogComponent;
    let fixture: ComponentFixture<DoenteSocioFamiliarDeleteDialogComponent>;
    let service: DoenteSocioFamiliarService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteSocioFamiliarDeleteDialogComponent]
      })
        .overrideTemplate(DoenteSocioFamiliarDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteSocioFamiliarDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteSocioFamiliarService);
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
