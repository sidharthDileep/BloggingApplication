import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterSuccessComponent } from './register-success.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('RegisterSuccessComponent', () => {
  let component: RegisterSuccessComponent;
  let fixture: ComponentFixture<RegisterSuccessComponent>;
  RouterTestingModule.withRoutes([
    { path: '/register-success', component: RegisterSuccessComponent}
  ])

  beforeEach(() => {
    
    TestBed.configureTestingModule({
      declarations: [RegisterSuccessComponent],
    });

    fixture = TestBed.createComponent(RegisterSuccessComponent);
    component = fixture.componentInstance;
  });

  it('should create the RegisterSuccessComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should render a success message', () => {
    fixture.detectChanges();
    const element: HTMLElement = fixture.nativeElement;
    const message = element.querySelector('.success-message');

    expect(message).toBeTruthy();
    expect(message!.textContent).toContain('Registration Successful'); // Adjust with your expected message
  });
});