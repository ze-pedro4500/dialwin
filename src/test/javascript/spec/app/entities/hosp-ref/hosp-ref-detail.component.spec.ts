import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { HospRefDetailComponent } from 'app/entities/hosp-ref/hosp-ref-detail.component';
import { HospRef } from 'app/shared/model/hosp-ref.model';

describe('Component Tests', () => {
  describe('HospRef Management Detail Component', () => {
    let comp: HospRefDetailComponent;
    let fixture: ComponentFixture<HospRefDetailComponent>;
    const route = ({ data: of({ hospRef: new HospRef(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [HospRefDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HospRefDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HospRefDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hospRef).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
