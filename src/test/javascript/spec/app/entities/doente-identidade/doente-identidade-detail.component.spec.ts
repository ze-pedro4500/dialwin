import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteIdentidadeDetailComponent } from 'app/entities/doente-identidade/doente-identidade-detail.component';
import { DoenteIdentidade } from 'app/shared/model/doente-identidade.model';

describe('Component Tests', () => {
  describe('DoenteIdentidade Management Detail Component', () => {
    let comp: DoenteIdentidadeDetailComponent;
    let fixture: ComponentFixture<DoenteIdentidadeDetailComponent>;
    const route = ({ data: of({ doenteIdentidade: new DoenteIdentidade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteIdentidadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoenteIdentidadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteIdentidadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doenteIdentidade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
