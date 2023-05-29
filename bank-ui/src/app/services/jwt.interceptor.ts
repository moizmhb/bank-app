import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';

// import { environment } from '../../environments/environment';
// import { AccountService } from '../_services';
import { UserService } from './user.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private userService: UserService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // add auth header with jwt if user is logged in and request is to the api url
    const user = this.userService.userValue;
    const isLoggedIn = user && user.accessToken;
    const isApiUrl = request.url.startsWith('http://localhost:82/'); //userService.apiUrl
    if (isApiUrl) {
        console.log("interceptor::")
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${user?.accessToken}`,
        },
      });
    }

    return next.handle(request);
  }
}
