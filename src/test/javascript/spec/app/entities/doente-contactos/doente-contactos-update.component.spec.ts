import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteContactosUpdateComponent } from 'app/entities/doente-contactos/doente-contactos-update.component';
import { DoenteContactosService } from 'app/entities/doente-contactos/doente-contactos.service';
import { DoenteContactos } from 'app/shared/model/doente-contactos.model';

describe('Component Tests', () => {
  describe('DoenteContactos Management Update Component', () => {
    let comp: DoenteContactosUpdateComponent;
    let fixture: ComponentFixture<DoenteContactosUpdateComponent>;
    let service: DoenteContactosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteContactosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoenteContactosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteContactosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteContactosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoenteContactos(123);
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
        const entity = new DoenteContactos();
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
