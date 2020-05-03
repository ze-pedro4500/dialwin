import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { GrauConfComponent } from 'app/entities/grau-conf/grau-conf.component';
import { GrauConfService } from 'app/entities/grau-conf/grau-conf.service';
import { GrauConf } from 'app/shared/model/grau-conf.model';

describe('Component Tests', () => {
  describe('GrauConf Management Component', () => {
    let comp: GrauConfComponent;
    let fixture: ComponentFixture<GrauConfComponent>;
    let service: GrauConfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [GrauConfComponent],
        providers: []
      })
        .overrideTemplate(GrauConfComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GrauConfComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrauConfService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GrauConf(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.grauConfs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
