import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { HospRefUpdateComponent } from 'app/entities/hosp-ref/hosp-ref-update.component';
import { HospRefService } from 'app/entities/hosp-ref/hosp-ref.service';
import { HospRef } from 'app/shared/model/hosp-ref.model';

describe('Component Tests', () => {
  describe('HospRef Management Update Component', () => {
    let comp: HospRefUpdateComponent;
    let fixture: ComponentFixture<HospRefUpdateComponent>;
    let service: HospRefService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [HospRefUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HospRefUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HospRefUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HospRefService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HospRef(123);
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
        const entity = new HospRef();
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
