import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPostComponent } from './add-post.component';
import { FormControl, FormGroup, FormsModule } from '@angular/forms';
import { AddPostService } from '../add-post.service';


describe('AddPostComponent', () => {
  let component: AddPostComponent;
  let fixture: ComponentFixture<AddPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormGroup, FormsModule],
      declarations: [ AddPostComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("it should create a new post", () => {
    const postService = fixture.debugElement.injector.get(AddPostService);
    fixture.detectChanges();
  });
});
