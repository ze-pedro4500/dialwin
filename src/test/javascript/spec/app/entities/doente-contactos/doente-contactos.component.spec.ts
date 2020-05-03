import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { DoenteContactosComponent } from 'app/entities/doente-contactos/doente-contactos.component';
import { DoenteContactosService } from 'app/entities/doente-contactos/doente-contactos.service';
import { DoenteContactos } from 'app/shared/model/doente-contactos.model';

describe('Component Tests', () => {
  describe('DoenteContactos Management Component', () => {
    let comp: DoenteContactosComponent;
    let fixture: ComponentFixture<DoenteContactosComponent>;
    let service: DoenteContactosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteContactosComponent],
        providers: []
      })
        .overrideTemplate(DoenteContactosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteContactosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteContactosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoenteContactos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doenteContactos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
