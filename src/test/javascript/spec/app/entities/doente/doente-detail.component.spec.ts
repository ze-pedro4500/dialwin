import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { DoenteDetailComponent } from 'app/entities/doente/doente-detail.component';
import { Doente } from 'app/shared/model/doente.model';

describe('Component Tests', () => {
  describe('Doente Management Detail Component', () => {
    let comp: DoenteDetailComponent;
    let fixture: ComponentFixture<DoenteDetailComponent>;
    const route = ({ data: of({ doente: new Doente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DoenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
