import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { ACESUpdateComponent } from 'app/entities/aces/aces-update.component';
import { ACESService } from 'app/entities/aces/aces.service';
import { ACES } from 'app/shared/model/aces.model';

describe('Component Tests', () => {
  describe('ACES Management Update Component', () => {
    let comp: ACESUpdateComponent;
    let fixture: ComponentFixture<ACESUpdateComponent>;
    let service: ACESService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [ACESUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ACESUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ACESUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ACESService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ACES(123);
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
        const entity = new ACES();
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
