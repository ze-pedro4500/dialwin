import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { GrauConfDeleteDialogComponent } from 'app/entities/grau-conf/grau-conf-delete-dialog.component';
import { GrauConfService } from 'app/entities/grau-conf/grau-conf.service';

describe('Component Tests', () => {
  describe('GrauConf Management Delete Component', () => {
    let comp: GrauConfDeleteDialogComponent;
    let fixture: ComponentFixture<GrauConfDeleteDialogComponent>;
    let service: GrauConfService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [GrauConfDeleteDialogComponent]
      })
        .overrideTemplate(GrauConfDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrauConfDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrauConfService);
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
