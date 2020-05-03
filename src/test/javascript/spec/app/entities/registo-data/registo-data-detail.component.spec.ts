import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialwinTestModule } from '../../../test.module';
import { RegistoDataDetailComponent } from 'app/entities/registo-data/registo-data-detail.component';
import { RegistoData } from 'app/shared/model/registo-data.model';

describe('Component Tests', () => {
  describe('RegistoData Management Detail Component', () => {
    let comp: RegistoDataDetailComponent;
    let fixture: ComponentFixture<RegistoDataDetailComponent>;
    const route = ({ data: of({ registoData: new RegistoData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [RegistoDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegistoDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegistoDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load registoData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.registoData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
