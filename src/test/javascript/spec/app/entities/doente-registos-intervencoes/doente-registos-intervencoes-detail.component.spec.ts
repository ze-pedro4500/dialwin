import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteRegistosIntervencoesDetailComponent } from 'app/entities/doente-registos-intervencoes/doente-registos-intervencoes-detail.component';
import { DoenteRegistosIntervencoes } from 'app/shared/model/doente-registos-intervencoes.model';

describe('Component Tests', () => {
  describe('DoenteRegistosIntervencoes Management Detail Component', () => {
    let comp: DoenteRegistosIntervencoesDetailComponent;
    let fixture: ComponentFixture<DoenteRegistosIntervencoesDetailComponent>;
    const route = ({ data: of({ doenteRegistosIntervencoes: new DoenteRegistosIntervencoes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteRegistosIntervencoesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoenteRegistosIntervencoesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteRegistosIntervencoesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doenteRegistosIntervencoes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
