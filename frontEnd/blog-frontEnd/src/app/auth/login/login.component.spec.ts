import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth.service';
import { LoginPayload } from '../login-payload';
import { of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [ReactiveFormsModule],
      providers: [
        {
          provide: AuthService,
          useValue: {
            login: () => of(true), // Mock the login method
          },
        },
        {
          provide: Router,
          useValue: {
            navigate: jasmine.createSpy('navigate'), // Spy on the navigate method
          },
        },
      ],
    });

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);

    fixture.detectChanges(); // Trigger initial change detection
  });

  it('should create the LoginComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should have a login form with username and password fields', () => {
    const usernameField = component.loginForm.get('username') as FormControl;
    const passwordField = component.loginForm.get('password') as FormControl;

    expect(usernameField).toBeTruthy();
    expect(passwordField).toBeTruthy();
  });

  it('should call AuthService.login() and navigate to home on successful login', () => {
    const loginPayload: LoginPayload = {
      usernameOrEmail: 'testuser',
      password: 'password123',
    };

    spyOn(authService, 'login').and.callThrough();

    component.loginForm.controls['usernameOrEmail'].setValue('testuser');
    component.loginForm.controls['password'].setValue('password123');
    component.onSubmit();

    expect(authService.login).toHaveBeenCalledWith(loginPayload);
    expect(router.navigate).toHaveBeenCalledWith(['/home']);
  });

  // Add more test cases as needed
});