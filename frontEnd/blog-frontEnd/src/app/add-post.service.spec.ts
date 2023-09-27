import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AddPostService } from './add-post.service';
import { PostPayload } from './add-post/post-payload';
import { PostPayloadResponse } from './add-post/post-payload-response';

describe('AddPostService', () => {
  let service: AddPostService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AddPostService],
    });

    service = TestBed.inject(AddPostService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a POST request to create a post', () => {
    const mockPostPayload: PostPayload = {
      category: "General",
      title: "Lessons",
      description: "My Experience",
      content: "Life Lessons"
    };
    service.addPost(mockPostPayload).subscribe();

    const req = httpTestingController.expectOne('http://localhost:9090/blog-write-api/api/v1/posts');
    expect(req.request.method).toEqual('POST');
    req.flush({}); // You can provide a mock response if needed

    httpTestingController.verify();
  });

  it('should send a GET request to retrieve all posts', () => {
    const mockResponse: PostPayloadResponse[] = [{
      "id": 47,
      "category": "dgrdfhdfhdfhyujfdfdfdfefdfsdfg",
      "title": "dfsdfdgdfgfgfdhhhjfdfdefwefwffgdfgddoovvv",
      "description": "defwefwfweffgdfdfhfdhhghfdefwefgdf",
      "content": "efwefefwefweffgdfgfhdhkghkgefwefefgdfgd"
  },
  {
      "id": 50,
      "category": "General",
      "title": "New Post",
      "description": "Dummy description",
      "content": "<p>Hello, I am working now!!!</p>"
  }];
    service.getAllPosts().subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpTestingController.expectOne('http://localhost:9090/blog-read-api/api/v1/posts/all');
    expect(req.request.method).toEqual('GET');
    req.flush({ content: mockResponse });

    httpTestingController.verify();
  });

  it('should send a GET request to retrieve a post by permalink', () => {
    const mockPermaLink = 1;
    const mockResponse: PostPayloadResponse = {
      id: 1,
      category: "Sports",
      title: "Still Time is there",
      description: "Perfect",
      content: "Heyyy"
    };
    service.getPost(mockPermaLink).subscribe((response) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpTestingController.expectOne(
      'http://localhost:9090/blog-read-api/api/v1/posts/' + mockPermaLink
    );
    expect(req.request.method).toEqual('GET');
    req.flush(mockResponse);

    httpTestingController.verify();
  });
});