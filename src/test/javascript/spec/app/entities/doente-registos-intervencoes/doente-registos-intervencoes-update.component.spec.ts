import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteRegistosIntervencoesUpdateComponent } from 'app/entities/doente-registos-intervencoes/doente-registos-intervencoes-update.component';
import { DoenteRegistosIntervencoesService } from 'app/entities/doente-registos-intervencoes/doente-registos-intervencoes.service';
import { DoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';

describe('Component Tests', () => {
  describe('DoenteRegistosIntervencoes Management Update Component', () => {
    let comp: DoenteRegistosIntervencoesUpdateComponent;
    let fixture: ComponentFixture<DoenteRegistosIntervencoesUpdateComponent>;
    let service: DoenteRegistosIntervencoesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteRegistosIntervencoesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoenteRegistosIntervencoesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteRegistosIntervencoesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteRegistosIntervencoesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoenteRegistosIntervencoes(123);
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
        const entity = new DoenteRegistosIntervencoes();
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
