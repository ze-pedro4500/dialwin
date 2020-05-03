import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { DoenteHistMovimentosComponent } from 'app/entities/doente-hist-movimentos/doente-hist-movimentos.component';
import { DoenteHistMovimentosService } from 'app/entities/doente-hist-movimentos/doente-hist-movimentos.service';
import { DoenteHistMovimentos } from 'app/shared/model/doente-hist-movimentos.model';

describe('Component Tests', () => {
  describe('DoenteHistMovimentos Management Component', () => {
    let comp: DoenteHistMovimentosComponent;
    let fixture: ComponentFixture<DoenteHistMovimentosComponent>;
    let service: DoenteHistMovimentosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteHistMovimentosComponent],
        providers: []
      })
        .overrideTemplate(DoenteHistMovimentosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteHistMovimentosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteHistMovimentosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoenteHistMovimentos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doenteHistMovimentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
