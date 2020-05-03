import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteIdentidadeUpdateComponent } from 'app/entities/doente-identidade/doente-identidade-update.component';
import { DoenteIdentidadeService } from 'app/entities/doente-identidade/doente-identidade.service';
import { DoenteIdentidade } from 'app/shared/model/doente-identidade.model';

describe('Component Tests', () => {
  describe('DoenteIdentidade Management Update Component', () => {
    let comp: DoenteIdentidadeUpdateComponent;
    let fixture: ComponentFixture<DoenteIdentidadeUpdateComponent>;
    let service: DoenteIdentidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteIdentidadeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoenteIdentidadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteIdentidadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteIdentidadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoenteIdentidade(123);
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
        const entity = new DoenteIdentidade();
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
