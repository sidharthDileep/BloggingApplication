import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { AuthService } from 'src/app/auth.service';

describe('RegisterComponent', () => {
  let fixture: ComponentFixture<RegisterComponent>;
  let component: RegisterComponent;
  let authServiceStub: Partial<AuthService>;

  authServiceStub = {
    register: () => of({}), // Import 'of' from 'rxjs' for mocking observable
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [ReactiveFormsModule],
      providers: [{ provide: AuthService, useValue: authServiceStub }],
    });

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
  });

  it('should create the RegisterComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should call AuthService.register() on form submission', () => {
    const authService = TestBed.inject(AuthService);
    const registerSpy = spyOn(authService, 'register').and.returnValue(of({}));

    component.onSubmit();

    expect(registerSpy).toHaveBeenCalled();
  });
});
