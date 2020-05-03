import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { SubSistemasComponent } from 'app/entities/sub-sistemas/sub-sistemas.component';
import { SubSistemasService } from 'app/entities/sub-sistemas/sub-sistemas.service';
import { SubSistemas } from 'app/shared/model/sub-sistemas.model';

describe('Component Tests', () => {
  describe('SubSistemas Management Component', () => {
    let comp: SubSistemasComponent;
    let fixture: ComponentFixture<SubSistemasComponent>;
    let service: SubSistemasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SubSistemasComponent],
        providers: []
      })
        .overrideTemplate(SubSistemasComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubSistemasComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubSistemasService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SubSistemas(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subSistemas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
