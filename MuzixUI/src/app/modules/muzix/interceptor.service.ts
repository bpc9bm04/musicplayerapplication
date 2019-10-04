import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http'
import { AuthenticationService } from './../authentication/authentication.service'
import { Observable } from 'rxjs/Observable';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private auth:AuthenticationService) {  }
    
    /**
     * Set token to the request header, before handling the request
     * @param request 
     * @param next 
     */
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {  
        console.log("Request url: ", request.url);
        let url = new URL(request.url);
        console.log("Request url hostname: ", url.hostname);
        if(url.hostname !== "api.napster.com") {  
          console.log("Token interceptor: set token to the request header!"); 
          request = request.clone({
            setHeaders: {
              Authorization: `Bearer ${this.auth.getToken()}`
            }   
          }); 
        } else {         
          request = request.clone({setHeaders: {}}); 
        }
        return next.handle(request);        
    }
}
