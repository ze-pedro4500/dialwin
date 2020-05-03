import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { SubSistemasDetailComponent } from 'app/entities/sub-sistemas/sub-sistemas-detail.component';
import { SubSistemas } from 'app/shared/model/sub-sistemas.model';

describe('Component Tests', () => {
  describe('SubSistemas Management Detail Component', () => {
    let comp: SubSistemasDetailComponent;
    let fixture: ComponentFixture<SubSistemasDetailComponent>;
    const route = ({ data: of({ subSistemas: new SubSistemas(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [SubSistemasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubSistemasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubSistemasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subSistemas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
