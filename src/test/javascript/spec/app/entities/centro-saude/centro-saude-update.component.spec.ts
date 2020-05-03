import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { CentroSaudeUpdateComponent } from 'app/entities/centro-saude/centro-saude-update.component';
import { CentroSaudeService } from 'app/entities/centro-saude/centro-saude.service';
import { CentroSaude } from 'app/shared/model/centro-saude.model';

describe('Component Tests', () => {
  describe('CentroSaude Management Update Component', () => {
    let comp: CentroSaudeUpdateComponent;
    let fixture: ComponentFixture<CentroSaudeUpdateComponent>;
    let service: CentroSaudeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [CentroSaudeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CentroSaudeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CentroSaudeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CentroSaudeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CentroSaude(123);
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
        const entity = new CentroSaude();
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
