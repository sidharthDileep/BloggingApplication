import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PostPayload} from './add-post/post-payload';
import {Observable} from 'rxjs';
import { PostPayloadResponse } from './add-post/post-payload-response';


@Injectable({
  providedIn: 'root'
})
export class AddPostService {

  constructor(private httpClient: HttpClient) { }

  addPost(postPayload: PostPayload){
    return this.httpClient.post('http://localhost:9090/blog-write-api/api/v1/posts', postPayload);
  }

  getAllPosts(): Observable<any>{
    return this.httpClient.get<any>("http://localhost:9090/blog-read-api/api/v1/posts/all");
  }

  getPost(permaLink: Number):Observable<PostPayloadResponse>{
    return this.httpClient.get<PostPayloadResponse>('http://localhost:9090/blog-read-api/api/v1/posts/' + permaLink);
  }
}
