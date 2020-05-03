import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteContactosOutrosUpdateComponent } from 'app/entities/doente-contactos-outros/doente-contactos-outros-update.component';
import { DoenteContactosOutrosService } from 'app/entities/doente-contactos-outros/doente-contactos-outros.service';
import { DoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';

describe('Component Tests', () => {
  describe('DoenteContactosOutros Management Update Component', () => {
    let comp: DoenteContactosOutrosUpdateComponent;
    let fixture: ComponentFixture<DoenteContactosOutrosUpdateComponent>;
    let service: DoenteContactosOutrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteContactosOutrosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoenteContactosOutrosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteContactosOutrosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteContactosOutrosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoenteContactosOutros(123);
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
        const entity = new DoenteContactosOutros();
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
