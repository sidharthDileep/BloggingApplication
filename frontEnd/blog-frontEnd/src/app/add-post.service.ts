import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PostPayload} from './add-post/post-payload';
import {Observable} from 'rxjs';
import { PostPayloadResponse } from './add-post/post-payload-response';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AddPostService {
  //'http://localhost:9090/blog-write-api/api/v1/posts'

  constructor(private httpClient: HttpClient) { }

  addPost(postPayload: PostPayload){
    return this.httpClient.post(environment.writeUrl, postPayload);
  }

  getAllPosts(): Observable<any>{
    return this.httpClient.get<any>(environment.readUrl + "/all");
  }

  getPost(permaLink: Number):Observable<PostPayloadResponse>{
    return this.httpClient.get<PostPayloadResponse>(environment.readUrl + '/' + permaLink);
  }
}
