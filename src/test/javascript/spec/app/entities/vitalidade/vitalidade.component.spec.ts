import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { VitalidadeComponent } from 'app/entities/vitalidade/vitalidade.component';
import { VitalidadeService } from 'app/entities/vitalidade/vitalidade.service';
import { Vitalidade } from 'app/shared/model/vitalidade.model';

describe('Component Tests', () => {
  describe('Vitalidade Management Component', () => {
    let comp: VitalidadeComponent;
    let fixture: ComponentFixture<VitalidadeComponent>;
    let service: VitalidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [VitalidadeComponent],
        providers: []
      })
        .overrideTemplate(VitalidadeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VitalidadeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VitalidadeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Vitalidade(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vitalidades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
