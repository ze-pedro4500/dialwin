import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { DoenteDiagnosticoSocialDeleteDialogComponent } from 'app/entities/doente-diagnostico-social/doente-diagnostico-social-delete-dialog.component';
import { DoenteDiagnosticoSocialService } from 'app/entities/doente-diagnostico-social/doente-diagnostico-social.service';

describe('Component Tests', () => {
  describe('DoenteDiagnosticoSocial Management Delete Component', () => {
    let comp: DoenteDiagnosticoSocialDeleteDialogComponent;
    let fixture: ComponentFixture<DoenteDiagnosticoSocialDeleteDialogComponent>;
    let service: DoenteDiagnosticoSocialService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteDiagnosticoSocialDeleteDialogComponent]
      })
        .overrideTemplate(DoenteDiagnosticoSocialDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteDiagnosticoSocialDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteDiagnosticoSocialService);
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
