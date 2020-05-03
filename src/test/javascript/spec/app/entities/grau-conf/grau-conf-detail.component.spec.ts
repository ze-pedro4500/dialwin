import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { GrauConfDetailComponent } from 'app/entities/grau-conf/grau-conf-detail.component';
import { GrauConf } from 'app/shared/model/grau-conf.model';

describe('Component Tests', () => {
  describe('GrauConf Management Detail Component', () => {
    let comp: GrauConfDetailComponent;
    let fixture: ComponentFixture<GrauConfDetailComponent>;
    const route = ({ data: of({ grauConf: new GrauConf(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [GrauConfDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GrauConfDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrauConfDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.grauConf).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
