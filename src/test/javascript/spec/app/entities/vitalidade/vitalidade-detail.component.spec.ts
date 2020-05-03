import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { VitalidadeDetailComponent } from 'app/entities/vitalidade/vitalidade-detail.component';
import { Vitalidade } from 'app/shared/model/vitalidade.model';

describe('Component Tests', () => {
  describe('Vitalidade Management Detail Component', () => {
    let comp: VitalidadeDetailComponent;
    let fixture: ComponentFixture<VitalidadeDetailComponent>;
    const route = ({ data: of({ vitalidade: new Vitalidade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [VitalidadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VitalidadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VitalidadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vitalidade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
