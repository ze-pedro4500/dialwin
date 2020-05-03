import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { RegistoDataComponent } from 'app/entities/registo-data/registo-data.component';
import { RegistoDataService } from 'app/entities/registo-data/registo-data.service';
import { RegistoData } from 'app/shared/model/registo-data.model';

describe('Component Tests', () => {
  describe('RegistoData Management Component', () => {
    let comp: RegistoDataComponent;
    let fixture: ComponentFixture<RegistoDataComponent>;
    let service: RegistoDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [RegistoDataComponent]
      })
        .overrideTemplate(RegistoDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegistoDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegistoDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegistoData(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.registoData && comp.registoData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
