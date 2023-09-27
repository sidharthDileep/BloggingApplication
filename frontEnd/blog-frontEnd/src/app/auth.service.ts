import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { RegisterPayload } from './register-payload';
import { map, Observable } from 'rxjs';
import { LoginPayload } from './auth/login-payload';
import { JwtAuthResponse } from './auth/jwt-auth-response';
import { LocalStorageService } from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  getLocalStorage() {
    throw new Error('Method not implemented.');
  }
  
  private url = 'http://localhost:9090/blog-auth-api/api/v1/auth/';
  //private url = 'http://localhost:8083/api/v1/auth/';

  constructor(private httpClient: HttpClient, private localStoraqeService: LocalStorageService) {
  }

  register(registerPayload : RegisterPayload) : Observable<any>{
    return this.httpClient.post(this.url + "user/signup", registerPayload);
  }

  login(loginPayload: LoginPayload) {
    return this.httpClient.post<JwtAuthResponse>(this.url + "signin", loginPayload).pipe(map(data => {
      //console.log(data);
      this.localStoraqeService.store('accessToken', data.accessToken);
      this.localStoraqeService.store('tokentype', data.tokentype);
      return true;
    }));
  }

  isAuthenticated(): boolean {
    return this.localStoraqeService.retrieve('accessToken') != null;
  }

  logout() {
    this.localStoraqeService.clear('accessToken');
    this.localStoraqeService.clear('tokentype');
  }
}
