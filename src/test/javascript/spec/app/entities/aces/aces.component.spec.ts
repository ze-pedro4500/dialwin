import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { ACESComponent } from 'app/entities/aces/aces.component';
import { ACESService } from 'app/entities/aces/aces.service';
import { ACES } from 'app/shared/model/aces.model';

describe('Component Tests', () => {
  describe('ACES Management Component', () => {
    let comp: ACESComponent;
    let fixture: ComponentFixture<ACESComponent>;
    let service: ACESService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [ACESComponent],
        providers: []
      })
        .overrideTemplate(ACESComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ACESComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ACESService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ACES(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aCES[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
