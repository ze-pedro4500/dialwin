import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteDiagnosticoSocialUpdateComponent } from 'app/entities/doente-diagnostico-social/doente-diagnostico-social-update.component';
import { DoenteDiagnosticoSocialService } from 'app/entities/doente-diagnostico-social/doente-diagnostico-social.service';
import { DoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';

describe('Component Tests', () => {
  describe('DoenteDiagnosticoSocial Management Update Component', () => {
    let comp: DoenteDiagnosticoSocialUpdateComponent;
    let fixture: ComponentFixture<DoenteDiagnosticoSocialUpdateComponent>;
    let service: DoenteDiagnosticoSocialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteDiagnosticoSocialUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DoenteDiagnosticoSocialUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteDiagnosticoSocialUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteDiagnosticoSocialService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoenteDiagnosticoSocial(123);
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
        const entity = new DoenteDiagnosticoSocial();
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
