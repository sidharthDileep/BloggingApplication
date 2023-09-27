import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderComponent } from './header.component';
import { AuthService } from '../auth.service';

describe('HeaderComponent', () => {
  let fixture: ComponentFixture<HeaderComponent>;
  let component: HeaderComponent;
  let authServiceStub: Partial<AuthService>;

  authServiceStub = {
    logout: () => {}
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderComponent],
      providers: [{ provide: AuthService, useValue: authServiceStub }],
    });

    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
  });

  it('should create the HeaderComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should call AuthService.logout() on logout', () => {
    const authService = TestBed.inject(AuthService);
    const logoutSpy = spyOn(authService, 'logout');

    component.logout();

    expect(logoutSpy).toHaveBeenCalled();
  });
});
