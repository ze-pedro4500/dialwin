import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { TurnosUpdateComponent } from 'app/entities/turnos/turnos-update.component';
import { TurnosService } from 'app/entities/turnos/turnos.service';
import { Turnos } from 'app/shared/model/turnos.model';

describe('Component Tests', () => {
  describe('Turnos Management Update Component', () => {
    let comp: TurnosUpdateComponent;
    let fixture: ComponentFixture<TurnosUpdateComponent>;
    let service: TurnosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [TurnosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TurnosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TurnosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TurnosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Turnos(123);
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
        const entity = new Turnos();
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
