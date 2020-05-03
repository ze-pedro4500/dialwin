import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteDiagnosticoSocialDetailComponent } from 'app/entities/doente-diagnostico-social/doente-diagnostico-social-detail.component';
import { DoenteDiagnosticoSocial } from 'app/shared/model/doente-diagnostico-social.model';

describe('Component Tests', () => {
  describe('DoenteDiagnosticoSocial Management Detail Component', () => {
    let comp: DoenteDiagnosticoSocialDetailComponent;
    let fixture: ComponentFixture<DoenteDiagnosticoSocialDetailComponent>;
    const route = ({ data: of({ doenteDiagnosticoSocial: new DoenteDiagnosticoSocial(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteDiagnosticoSocialDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoenteDiagnosticoSocialDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteDiagnosticoSocialDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doenteDiagnosticoSocial).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
