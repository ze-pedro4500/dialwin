import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DialwinTestModule } from '../../../test.module';
import { DoenteSocioFamiliarComponent } from 'app/entities/doente-socio-familiar/doente-socio-familiar.component';
import { DoenteSocioFamiliarService } from 'app/entities/doente-socio-familiar/doente-socio-familiar.service';
import { DoenteSocioFamiliar } from 'app/shared/model/doente-socio-familiar.model';

describe('Component Tests', () => {
  describe('DoenteSocioFamiliar Management Component', () => {
    let comp: DoenteSocioFamiliarComponent;
    let fixture: ComponentFixture<DoenteSocioFamiliarComponent>;
    let service: DoenteSocioFamiliarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DialwinTestModule],
        declarations: [DoenteSocioFamiliarComponent],
        providers: []
      })
        .overrideTemplate(DoenteSocioFamiliarComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoenteSocioFamiliarComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoenteSocioFamiliarService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DoenteSocioFamiliar(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.doenteSocioFamiliars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
