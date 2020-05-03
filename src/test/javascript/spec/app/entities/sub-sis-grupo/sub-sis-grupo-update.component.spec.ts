import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { SubSisGrupoUpdateComponent } from 'app/entities/sub-sis-grupo/sub-sis-grupo-update.component';
import { SubSisGrupoService } from 'app/entities/sub-sis-grupo/sub-sis-grupo.service';
import { SubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';

describe('Component Tests', () => {
  describe('SubSisGrupo Management Update Component', () => {
    let comp: SubSisGrupoUpdateComponent;
    let fixture: ComponentFixture<SubSisGrupoUpdateComponent>;
    let service: SubSisGrupoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SubSisGrupoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubSisGrupoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubSisGrupoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubSisGrupoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubSisGrupo(123);
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
        const entity = new SubSisGrupo();
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
