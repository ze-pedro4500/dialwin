import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { DoenteIdentidadeComponent } from 'app/entities/doente-identidade/doente-identidade.component';
import { DoenteIdentidadeService } from 'app/entities/doente-identidade/doente-identidade.service';
import { DoenteIdentidade } from 'app/shared/model/doente-identidade.model';

describe('Component Tests', () => {
  describe('DoenteIdentidade Management Component', () => {
    let comp: DoenteIdentidadeComponent;
    let fixture: ComponentFixture<DoenteIdentidadeComponent>;
    let service: DoenteIdentidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteIdentidadeComponent],
        providers: []
      })
        .overrideTemplate(DoenteIdentidadeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteIdentidadeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteIdentidadeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoenteIdentidade(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doenteIdentidades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
