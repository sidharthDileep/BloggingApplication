import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { AddPostService } from '../add-post.service';
import { of } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let mockPostService: jasmine.SpyObj<AddPostService>;

  beforeEach(() => {
    mockPostService = jasmine.createSpyObj('AddPostService', ['getAllPosts']);

    TestBed.configureTestingModule({
      declarations: [HomeComponent],
      providers: [{ provide: AddPostService, useValue: mockPostService }],
    });

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
  });

  it('should create the HomeComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the component properties correctly', () => {
    expect(component.data).toEqual([]);
    expect(component.page).toEqual(1);
    expect(component.count).toEqual(0);
    expect(component.items).toEqual(5);
    expect(component.p).toEqual(0);
    expect(component.s).toEqual(5);
    expect(component.posts).toBeUndefined();
  });

  it('should fetch posts on ngOnInit', () => {
    const mockPosts = [
          {
              "id": 34,
              "category": "Finance",
              "title": "Stock Market",
              "description": "I shall conquer stock market",
              "content": "Stock Market has high potential, I want to take a piece of it",
              "comments": null
          },
          {
              "id": 50,
              "category": "General",
              "title": "New Post",
              "description": "Dummy description",
              "content": "<p>Hello, I am working now!!!</p>",
              "comments": null
          }
      ];
    mockPostService.getAllPosts.and.returnValue(of({ content: mockPosts }));

    component.ngOnInit();

    expect(mockPostService.getAllPosts).toHaveBeenCalled();
    expect(component.posts).toEqual(mockPosts);
    expect(component.count).toEqual(mockPosts.length);
  });

  it('should update page and fetch posts on onTableDataChange', () => {
    const mockPage = 2;
    spyOn(component, 'fetchPosts');
    
    component.onTableDataChange(mockPage);

    expect(component.page).toEqual(mockPage);
    expect(component.fetchPosts).toHaveBeenCalled();
  });

  it('should fetch posts correctly', () => {
    const mockPosts = [
      {
          "id": 34,
          "category": "Finance",
          "title": "Stock Market",
          "description": "I shall conquer stock market",
          "content": "Stock Market has high potential, I want to take a piece of it",
          "comments": null
      },
      {
          "id": 50,
          "category": "General",
          "title": "New Post",
          "description": "Dummy description",
          "content": "<p>Hello, I am working now!!!</p>",
          "comments": null
      }
  ];
    mockPostService.getAllPosts.and.returnValue(of({ content: mockPosts }));

    component.fetchPosts();

    expect(mockPostService.getAllPosts).toHaveBeenCalled();
    expect(component.posts).toEqual(mockPosts);
    expect(component.count).toEqual(mockPosts.length);
  });
});


describe('HomeComponent', () => {
  let fixture: ComponentFixture<HomeComponent>;
  let component: HomeComponent;
  let addPostServiceStub: Partial<AddPostService>;

  addPostServiceStub = {
    getAllPosts: () => of({ content: [] }), // Import 'of' from 'rxjs' for mocking observable
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [NgxPaginationModule],
      providers: [{ provide: AddPostService, useValue: addPostServiceStub }],
    });

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
  });

  it('should create the HomeComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should call AddPostService.getAllPosts() on initialization', () => {
    const addPostService = TestBed.inject(AddPostService);
    const getAllPostsSpy = spyOn(addPostService, 'getAllPosts').and.returnValue(of({ content: [] }));

    component.ngOnInit();

    expect(getAllPostsSpy).toHaveBeenCalled();
  });
});