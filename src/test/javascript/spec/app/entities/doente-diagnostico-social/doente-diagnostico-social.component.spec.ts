import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { DoenteDiagnosticoSocialComponent } from 'app/entities/doente-diagnostico-social/doente-diagnostico-social.component';
import { DoenteDiagnosticoSocialService } from 'app/entities/doente-diagnostico-social/doente-diagnostico-social.service';
import { DoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';

describe('Component Tests', () => {
  describe('DoenteDiagnosticoSocial Management Component', () => {
    let comp: DoenteDiagnosticoSocialComponent;
    let fixture: ComponentFixture<DoenteDiagnosticoSocialComponent>;
    let service: DoenteDiagnosticoSocialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteDiagnosticoSocialComponent],
        providers: []
      })
        .overrideTemplate(DoenteDiagnosticoSocialComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteDiagnosticoSocialComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteDiagnosticoSocialService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoenteDiagnosticoSocial(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doenteDiagnosticoSocials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
