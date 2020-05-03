import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteSocioFamiliarUpdateComponent } from 'app/entities/doente-socio-familiar/doente-socio-familiar-update.component';
import { DoenteSocioFamiliarService } from 'app/entities/doente-socio-familiar/doente-socio-familiar.service';
import { DoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

describe('Component Tests', () => {
  describe('DoenteSocioFamiliar Management Update Component', () => {
    let comp: DoenteSocioFamiliarUpdateComponent;
    let fixture: ComponentFixture<DoenteSocioFamiliarUpdateComponent>;
    let service: DoenteSocioFamiliarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteSocioFamiliarUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoenteSocioFamiliarUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteSocioFamiliarUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteSocioFamiliarService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoenteSocioFamiliar(123);
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
        const entity = new DoenteSocioFamiliar();
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
