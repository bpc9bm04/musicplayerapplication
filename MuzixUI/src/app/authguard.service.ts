import { CanActivate } from "@angular/router/src/interfaces";
import { Injectable } from "@angular/core";
import { Router } from '@angular/router';
import { AuthenticationService } from "./modules/authentication/authentication.service";


@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private route:Router, private authService: AuthenticationService){ } 

    canActivate(){
        if(!this.authService.isTokenExpired()){
            console.log("Inside canActivate: token is valid!");
            return true;
        }
        this.route.navigate(['/login']);
        return false;
    }
}