import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AddPostService } from '../add-post.service';
import { PostPayload } from '../add-post/post-payload';
import { PostPayloadResponse } from '../add-post/post-payload-response';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  data: Array<PostPayloadResponse>;
  page: number = 1;
  count: number = 0;
  items: number = 5;
  p: number = 0;
  s: number = 5;
  posts!: Array<PostPayloadResponse>;
  constructor(private postService: AddPostService) { 
    this.data = new Array<any>();
  }

  onTableDataChange(event: any) {
    this.page = event;
    this.fetchPosts();
  }

  ngOnInit() {
    //this.posts = 
    this.fetchPosts();
  }

  fetchPosts() : void {
    this.postService.getAllPosts().subscribe((data) => {
      //console.log(data);
      this.posts = data.content;
      this.count = this.data.length;

    });
  }

}
