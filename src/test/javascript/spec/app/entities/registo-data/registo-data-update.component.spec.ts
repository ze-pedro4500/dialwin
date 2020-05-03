import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { RegistoDataUpdateComponent } from 'app/entities/registo-data/registo-data-update.component';
import { RegistoDataService } from 'app/entities/registo-data/registo-data.service';
import { RegistoData } from 'app/shared/model/registo-data.model';

describe('Component Tests', () => {
  describe('RegistoData Management Update Component', () => {
    let comp: RegistoDataUpdateComponent;
    let fixture: ComponentFixture<RegistoDataUpdateComponent>;
    let service: RegistoDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [RegistoDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegistoDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegistoDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegistoDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegistoData(123);
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
        const entity = new RegistoData();
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
