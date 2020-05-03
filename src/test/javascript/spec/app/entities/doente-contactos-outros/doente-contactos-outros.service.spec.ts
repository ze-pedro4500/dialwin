import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DoenteContactosOutrosService } from 'app/entities/doente-contactos-outros/doente-contactos-outros.service';
import { IDoenteContactosOutros, DoenteContactosOutros } from 'app/shared/model/doente-contactos-outros.model';

describe('Service Tests', () => {
  describe('DoenteContactosOutros Service', () => {
    let injector: TestBed;
    let service: DoenteContactosOutrosService;
    let httpMock: HttpTestingController;
    let elemDefault: IDoenteContactosOutros;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DoenteContactosOutrosService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DoenteContactosOutros(0, 'AAAAAAA', 'AAAAAAA', false, 0, 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a DoenteContactosOutros', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DoenteContactosOutros(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DoenteContactosOutros', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            parentesco: 'BBBBBB',
            coabita: true,
            telef: 1,
            ocupacao: 'BBBBBB',
            obs: 'BBBBBB',
            preferencial: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of DoenteContactosOutros', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            parentesco: 'BBBBBB',
            coabita: true,
            telef: 1,
            ocupacao: 'BBBBBB',
            obs: 'BBBBBB',
            preferencial: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DoenteContactosOutros', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
