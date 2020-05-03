import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { SitProfUpdateComponent } from 'app/entities/sit-prof/sit-prof-update.component';
import { SitProfService } from 'app/entities/sit-prof/sit-prof.service';
import { SitProf } from 'app/shared/model/sit-prof.model';

describe('Component Tests', () => {
  describe('SitProf Management Update Component', () => {
    let comp: SitProfUpdateComponent;
    let fixture: ComponentFixture<SitProfUpdateComponent>;
    let service: SitProfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SitProfUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SitProfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SitProfUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SitProfService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SitProf(123);
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
        const entity = new SitProf();
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
