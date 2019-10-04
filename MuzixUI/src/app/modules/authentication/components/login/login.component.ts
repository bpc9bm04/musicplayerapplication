import { Component, OnInit } from '@angular/core';
import { User } from './../../user';
import { Router } from '@angular/router';
import { AuthenticationService } from './../../authentication.service'
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  newUser:User;

  constructor(private authService:AuthenticationService, private router:Router) {
    this.newUser = new User;
  }

  /**
   * Call to login to the application by setting the token from the header
   * and land on the popular page
   */
  loginUser() {
    this.authService.loginUser(this.newUser).subscribe((data) => {
      console.log('User logged in!');
      if(data['token']){
        this.authService.setToken(data['token'])
        console.log('Token: ', data['token']);
        this.router.navigate(['/tracks/top']);
      }      
    })
  }
}
