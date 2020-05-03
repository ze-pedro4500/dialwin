import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { HorarioDoenteComponent } from 'app/entities/horario-doente/horario-doente.component';
import { HorarioDoenteService } from 'app/entities/horario-doente/horario-doente.service';
import { HorarioDoente } from 'app/shared/model/horario-doente.model';

describe('Component Tests', () => {
  describe('HorarioDoente Management Component', () => {
    let comp: HorarioDoenteComponent;
    let fixture: ComponentFixture<HorarioDoenteComponent>;
    let service: HorarioDoenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [HorarioDoenteComponent],
        providers: []
      })
        .overrideTemplate(HorarioDoenteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HorarioDoenteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HorarioDoenteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HorarioDoente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.horarioDoentes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
