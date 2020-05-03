import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { DoenteRegistosIntervencoesComponent } from 'app/entities/doente-registos-intervencoes/doente-registos-intervencoes.component';
import { DoenteRegistosIntervencoesService } from 'app/entities/doente-registos-intervencoes/doente-registos-intervencoes.service';
import { DoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';

describe('Component Tests', () => {
  describe('DoenteRegistosIntervencoes Management Component', () => {
    let comp: DoenteRegistosIntervencoesComponent;
    let fixture: ComponentFixture<DoenteRegistosIntervencoesComponent>;
    let service: DoenteRegistosIntervencoesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteRegistosIntervencoesComponent],
        providers: []
      })
        .overrideTemplate(DoenteRegistosIntervencoesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteRegistosIntervencoesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteRegistosIntervencoesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoenteRegistosIntervencoes(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doenteRegistosIntervencoes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
