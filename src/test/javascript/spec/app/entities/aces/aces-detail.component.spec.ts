import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { ACESDetailComponent } from 'app/entities/aces/aces-detail.component';
import { ACES } from 'app/shared/model/aces.model';

describe('Component Tests', () => {
  describe('ACES Management Detail Component', () => {
    let comp: ACESDetailComponent;
    let fixture: ComponentFixture<ACESDetailComponent>;
    const route = ({ data: of({ aCES: new ACES(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [ACESDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ACESDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ACESDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aCES).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
