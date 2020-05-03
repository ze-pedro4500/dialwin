import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { HorarioDoenteUpdateComponent } from 'app/entities/horario-doente/horario-doente-update.component';
import { HorarioDoenteService } from 'app/entities/horario-doente/horario-doente.service';
import { HorarioDoente } from 'app/shared/model/horario-doente.model';

describe('Component Tests', () => {
  describe('HorarioDoente Management Update Component', () => {
    let comp: HorarioDoenteUpdateComponent;
    let fixture: ComponentFixture<HorarioDoenteUpdateComponent>;
    let service: HorarioDoenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [HorarioDoenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HorarioDoenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HorarioDoenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HorarioDoenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HorarioDoente(123);
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
        const entity = new HorarioDoente();
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
