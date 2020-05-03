import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { GrauConfUpdateComponent } from 'app/entities/grau-conf/grau-conf-update.component';
import { GrauConfService } from 'app/entities/grau-conf/grau-conf.service';
import { GrauConf } from 'app/shared/model/grau-conf.model';

describe('Component Tests', () => {
  describe('GrauConf Management Update Component', () => {
    let comp: GrauConfUpdateComponent;
    let fixture: ComponentFixture<GrauConfUpdateComponent>;
    let service: GrauConfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [GrauConfUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GrauConfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GrauConfUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrauConfService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GrauConf(123);
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
        const entity = new GrauConf();
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
