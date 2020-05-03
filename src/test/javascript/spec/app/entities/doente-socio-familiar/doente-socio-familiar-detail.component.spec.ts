import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteSocioFamiliarDetailComponent } from 'app/entities/doente-socio-familiar/doente-socio-familiar-detail.component';
import { DoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

describe('Component Tests', () => {
  describe('DoenteSocioFamiliar Management Detail Component', () => {
    let comp: DoenteSocioFamiliarDetailComponent;
    let fixture: ComponentFixture<DoenteSocioFamiliarDetailComponent>;
    const route = ({ data: of({ doenteSocioFamiliar: new DoenteSocioFamiliar(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteSocioFamiliarDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoenteSocioFamiliarDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteSocioFamiliarDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doenteSocioFamiliar).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
