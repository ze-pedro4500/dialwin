import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';

describe('Service Tests', () => {
  describe('UserProfile Service', () => {
    let injector: TestBed;
    let service: UserProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserProfile;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(UserProfileService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserProfile(0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
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

      it('should create a UserProfile', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new UserProfile(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a UserProfile', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            demograf: 1,
            social: 1,
            procClin: 1,
            dialEnf: 1,
            dialStat: 1,
            qualiStat: 1,
            labLink: 1,
            gidLink: 1,
            asterixFarma: 1,
            asterixGabMed: 1
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

      it('should return a list of UserProfile', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            demograf: 1,
            social: 1,
            procClin: 1,
            dialEnf: 1,
            dialStat: 1,
            qualiStat: 1,
            labLink: 1,
            gidLink: 1,
            asterixFarma: 1,
            asterixGabMed: 1
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

      it('should delete a UserProfile', () => {
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
