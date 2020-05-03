import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { HabitUpdateComponent } from 'app/entities/habit/habit-update.component';
import { HabitService } from 'app/entities/habit/habit.service';
import { Habit } from 'app/shared/model/habit.model';

describe('Component Tests', () => {
  describe('Habit Management Update Component', () => {
    let comp: HabitUpdateComponent;
    let fixture: ComponentFixture<HabitUpdateComponent>;
    let service: HabitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [HabitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HabitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HabitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HabitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Habit(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Habit();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
