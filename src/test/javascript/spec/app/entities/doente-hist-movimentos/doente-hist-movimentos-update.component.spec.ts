import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteHistMovimentosUpdateComponent } from 'app/entities/doente-hist-movimentos/doente-hist-movimentos-update.component';
import { DoenteHistMovimentosService } from 'app/entities/doente-hist-movimentos/doente-hist-movimentos.service';
import { DoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';

describe('Component Tests', () => {
  describe('DoenteHistMovimentos Management Update Component', () => {
    let comp: DoenteHistMovimentosUpdateComponent;
    let fixture: ComponentFixture<DoenteHistMovimentosUpdateComponent>;
    let service: DoenteHistMovimentosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteHistMovimentosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoenteHistMovimentosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteHistMovimentosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteHistMovimentosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoenteHistMovimentos(123);
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
        const entity = new DoenteHistMovimentos();
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
