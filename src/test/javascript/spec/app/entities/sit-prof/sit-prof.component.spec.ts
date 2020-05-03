import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { SitProfComponent } from 'app/entities/sit-prof/sit-prof.component';
import { SitProfService } from 'app/entities/sit-prof/sit-prof.service';
import { SitProf } from 'app/shared/model/sit-prof.model';

describe('Component Tests', () => {
  describe('SitProf Management Component', () => {
    let comp: SitProfComponent;
    let fixture: ComponentFixture<SitProfComponent>;
    let service: SitProfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SitProfComponent],
        providers: []
      })
        .overrideTemplate(SitProfComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SitProfComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SitProfService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SitProf(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sitProfs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
