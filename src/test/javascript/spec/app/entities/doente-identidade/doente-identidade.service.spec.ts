import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DoenteIdentidadeService } from 'app/entities/doente-identidade/doente-identidade.service';
import { IDoenteIdentidade, DoenteIdentidade } from 'app/shared/model/doente-identidade.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';

describe('Service Tests', () => {
  describe('DoenteIdentidade Service', () => {
    let injector: TestBed;
    let service: DoenteIdentidadeService;
    let httpMock: HttpTestingController;
    let elemDefault: IDoenteIdentidade;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DoenteIdentidadeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DoenteIdentidade(
        0,
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        Sexo.Masculino,
        0,
        0,
        0,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataNasc: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a DoenteIdentidade', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataNasc: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNasc: currentDate
          },
          returnedFromService
        );
        service
          .create(new DoenteIdentidade(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DoenteIdentidade', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dataNasc: currentDate.format(DATE_FORMAT),
            altura: 1,
            morada: 'BBBBBB',
            codPost: 'BBBBBB',
            freguesia: 'BBBBBB',
            nif: 1,
            medFam: 'BBBBBB',
            sexo: 'BBBBBB',
            telef: 1,
            telef2: 1,
            docId: 1,
            nBenef: 1,
            nUtente: 1,
            numProcHosp: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNasc: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of DoenteIdentidade', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dataNasc: currentDate.format(DATE_FORMAT),
            altura: 1,
            morada: 'BBBBBB',
            codPost: 'BBBBBB',
            freguesia: 'BBBBBB',
            nif: 1,
            medFam: 'BBBBBB',
            sexo: 'BBBBBB',
            telef: 1,
            telef2: 1,
            docId: 1,
            nBenef: 1,
            nUtente: 1,
            numProcHosp: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNasc: currentDate
          },
          returnedFromService
        );
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

      it('should delete a DoenteIdentidade', () => {
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
