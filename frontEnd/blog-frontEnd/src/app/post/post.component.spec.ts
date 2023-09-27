import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { PostComponent } from './post.component';
import { AddPostService } from '../add-post.service';
import { PostPayloadResponse } from '../add-post/post-payload-response';

describe('PostComponent', () => {
  let component: PostComponent;
  let fixture: ComponentFixture<PostComponent>;
  let mockActivatedRoute: any;
  let mockPostService: Partial<AddPostService>;

  beforeEach(() => {
    mockActivatedRoute = {
      params: of({ id: 1 }), // You can adjust the mock parameters as needed
    };

    mockPostService = {
      getPost: () => of({ /* mock post data */ } as PostPayloadResponse), // Adjust with your mock data
    };

    TestBed.configureTestingModule({
      declarations: [PostComponent],
      providers: [
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: AddPostService, useValue: mockPostService },
      ],
    });

    fixture = TestBed.createComponent(PostComponent);
    component = fixture.componentInstance;
  });

  it('should create the PostComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should retrieve and display post data', () => {
    const postService = TestBed.inject(AddPostService);
    spyOn(postService, 'getPost').and.callThrough();

    fixture.detectChanges();

    expect(postService.getPost).toHaveBeenCalledWith(1); // Check if the service method was called with the correct parameter
    expect(component.post).toEqual({ /* mock post data */ } as PostPayloadResponse); // Adjust with your mock data
  });
});