import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterSuccessComponent } from './auth/register-success/register-success.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import {NgxWebstorageModule} from 'ngx-webstorage';
import { HttpClientInterceptor } from './http-client-interceptor';
import { HomeComponent } from './home/home.component';
import { AddPostComponent } from './add-post/add-post.component';
import {EditorModule} from '@tinymce/tinymce-angular';
import { PostComponent } from './post/post.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { AuthGuard } from './auth.guard';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    RegisterSuccessComponent,
    HomeComponent,
    AddPostComponent,
    PostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxPaginationModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    EditorModule,
    NgxWebstorageModule.forRoot(),
    RouterModule.forRoot([
      {path: '', component: HomeComponent},
      {path: 'register', component: RegisterComponent},
      {path: 'login', component: LoginComponent},
      {path: 'home', component: HomeComponent},
      {path: 'add-post', component: AddPostComponent, canActivate: [AuthGuard]},
      {path: 'post/:id', component: PostComponent},
      {path: 'register-success', component: RegisterSuccessComponent}
    ])
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: HttpClientInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
