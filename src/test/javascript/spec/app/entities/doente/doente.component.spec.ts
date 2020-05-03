import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { DoenteComponent } from 'app/entities/doente/doente.component';
import { DoenteService } from 'app/entities/doente/doente.service';
import { Doente } from 'app/shared/model/doente.model';

describe('Component Tests', () => {
  describe('Doente Management Component', () => {
    let comp: DoenteComponent;
    let fixture: ComponentFixture<DoenteComponent>;
    let service: DoenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteComponent],
        providers: []
      })
        .overrideTemplate(DoenteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Doente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doentes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
