import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { LocalStorageService, NgxWebstorageModule } from 'ngx-webstorage';

describe('AuthService', () => {
  let service: AuthService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let localStorageService: LocalStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, NgxWebstorageModule.forRoot()],
      providers: [AuthService, LocalStorageService],
    });

    service = TestBed.inject(AuthService);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    localStorageService = TestBed.inject(LocalStorageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should register a user', () => {
    const mockRegisterPayload = { 
      name: "Sidharth",
      username: "sidhu",
      email: "sidharthSidhu@gmail.com",
      password: "mypassword"
    };
    service.register(mockRegisterPayload).subscribe();

    const req = httpTestingController.expectOne('http://localhost:9090/blog-auth-api/api/v1/auth/user/signup');
    expect(req.request.method).toEqual('POST');
    req.flush({}); // You can provide a mock response if needed

    httpTestingController.verify();
  });

  it('should log in a user and store tokens', () => {
    const mockLoginPayload = { 
      usernameOrEmail: "sidhu",
      password: "mypassword"
    };
    const mockAuthResponse = {
      "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaWRodUNUU0BnbWFpbC5jb20iLCJpYXQiOjE2OTU2NTY2NDIsImV4cCI6MTY5NjI2MTQ0Mn0.4eQBDp3kP4sYIiNpjfKJoUxq4g4Tyt57UtNBIGrg4OQuktHEO7mVCVWKo1V9KDxKS_UiA1rZ_DJqudclshUfAg",
      "tokentype": "Bearer"
  };

    service.login(mockLoginPayload).subscribe((result) => {
      expect(result).toBeTruthy();
      expect(localStorageService.retrieve('accessToken')).toBeTruthy();
    });

    const req = httpTestingController.expectOne('http://localhost:9090/blog-auth-api/api/v1/auth/signin');
    expect(req.request.method).toEqual('POST');
    req.flush(mockAuthResponse);

    httpTestingController.verify();
  });

  // Add more test cases for isAuthenticated, logout, etc.
});
