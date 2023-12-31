import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LocalStorageService } from "ngx-webstorage";
import { Observable } from "rxjs";

@Injectable()
export class HttpClientInterceptor implements HttpInterceptor {
  constructor(private $localStorage: LocalStorageService) {

  }

  intercept(req: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.$localStorage.retrieve("accessToken");
    console.log('jwt token ' + token);
    if (token) {
      const cloned = req.clone({
        headers: req.headers.set("Authorization",
          "Bearer " + token)
      });

      return next.handle(cloned);
    }
    else {
      return next.handle(req);
    }
  }
}