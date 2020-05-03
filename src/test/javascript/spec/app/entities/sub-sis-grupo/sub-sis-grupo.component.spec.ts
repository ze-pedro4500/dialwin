import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { SubSisGrupoComponent } from 'app/entities/sub-sis-grupo/sub-sis-grupo.component';
import { SubSisGrupoService } from 'app/entities/sub-sis-grupo/sub-sis-grupo.service';
import { SubSisGrupo } from 'app/shared/model/sub-sis-grupo.model';

describe('Component Tests', () => {
  describe('SubSisGrupo Management Component', () => {
    let comp: SubSisGrupoComponent;
    let fixture: ComponentFixture<SubSisGrupoComponent>;
    let service: SubSisGrupoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SubSisGrupoComponent],
        providers: []
      })
        .overrideTemplate(SubSisGrupoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubSisGrupoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubSisGrupoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SubSisGrupo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subSisGrupos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
