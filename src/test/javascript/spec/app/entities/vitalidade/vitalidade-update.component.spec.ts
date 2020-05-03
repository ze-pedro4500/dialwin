import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { VitalidadeUpdateComponent } from 'app/entities/vitalidade/vitalidade-update.component';
import { VitalidadeService } from 'app/entities/vitalidade/vitalidade.service';
import { Vitalidade } from 'app/shared/model/vitalidade.model';

describe('Component Tests', () => {
  describe('Vitalidade Management Update Component', () => {
    let comp: VitalidadeUpdateComponent;
    let fixture: ComponentFixture<VitalidadeUpdateComponent>;
    let service: VitalidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [VitalidadeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VitalidadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VitalidadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VitalidadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vitalidade(123);
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
        const entity = new Vitalidade();
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
