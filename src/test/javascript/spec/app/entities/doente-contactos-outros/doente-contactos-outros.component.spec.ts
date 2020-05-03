import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { DoenteContactosOutrosComponent } from 'app/entities/doente-contactos-outros/doente-contactos-outros.component';
import { DoenteContactosOutrosService } from 'app/entities/doente-contactos-outros/doente-contactos-outros.service';
import { DoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';

describe('Component Tests', () => {
  describe('DoenteContactosOutros Management Component', () => {
    let comp: DoenteContactosOutrosComponent;
    let fixture: ComponentFixture<DoenteContactosOutrosComponent>;
    let service: DoenteContactosOutrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteContactosOutrosComponent],
        providers: []
      })
        .overrideTemplate(DoenteContactosOutrosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteContactosOutrosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteContactosOutrosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoenteContactosOutros(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doenteContactosOutros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
