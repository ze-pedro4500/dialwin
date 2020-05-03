import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { SubSistemasUpdateComponent } from 'app/entities/sub-sistemas/sub-sistemas-update.component';
import { SubSistemasService } from 'app/entities/sub-sistemas/sub-sistemas.service';
import { SubSistemas } from 'app/shared/model/sub-sistemas.model';

describe('Component Tests', () => {
  describe('SubSistemas Management Update Component', () => {
    let comp: SubSistemasUpdateComponent;
    let fixture: ComponentFixture<SubSistemasUpdateComponent>;
    let service: SubSistemasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SubSistemasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubSistemasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubSistemasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubSistemasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubSistemas(123);
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
        const entity = new SubSistemas();
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
