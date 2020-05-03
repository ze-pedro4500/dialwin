import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { CentroSaudeComponent } from 'app/entities/centro-saude/centro-saude.component';
import { CentroSaudeService } from 'app/entities/centro-saude/centro-saude.service';
import { CentroSaude } from 'app/shared/model/centro-saude.model';

describe('Component Tests', () => {
  describe('CentroSaude Management Component', () => {
    let comp: CentroSaudeComponent;
    let fixture: ComponentFixture<CentroSaudeComponent>;
    let service: CentroSaudeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [CentroSaudeComponent],
        providers: []
      })
        .overrideTemplate(CentroSaudeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CentroSaudeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CentroSaudeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CentroSaude(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.centroSaudes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
