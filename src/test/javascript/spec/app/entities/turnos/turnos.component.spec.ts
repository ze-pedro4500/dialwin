import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { TurnosComponent } from 'app/entities/turnos/turnos.component';
import { TurnosService } from 'app/entities/turnos/turnos.service';
import { Turnos } from 'app/shared/model/turnos.model';

describe('Component Tests', () => {
  describe('Turnos Management Component', () => {
    let comp: TurnosComponent;
    let fixture: ComponentFixture<TurnosComponent>;
    let service: TurnosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [TurnosComponent],
        providers: []
      })
        .overrideTemplate(TurnosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TurnosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TurnosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Turnos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.turnos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
