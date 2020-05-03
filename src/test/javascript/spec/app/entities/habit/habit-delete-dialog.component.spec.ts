import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DialwinTestModule } from '../../../test.module';
import { HabitDeleteDialogComponent } from 'app/entities/habit/habit-delete-dialog.component';
import { HabitService } from 'app/entities/habit/habit.service';

describe('Component Tests', () => {
  describe('Habit Management Delete Component', () => {
    let comp: HabitDeleteDialogComponent;
    let fixture: ComponentFixture<HabitDeleteDialogComponent>;
    let service: HabitService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [HabitDeleteDialogComponent]
      })
        .overrideTemplate(HabitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HabitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HabitService);
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
