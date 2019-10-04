import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from './../../user'
import { AuthenticationService } from './../../authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  newUser:User;

  constructor(private authService:AuthenticationService, private router:Router, private matSnackbar: MatSnackBar) {
    this.newUser = new User();
  }

  /**
   * Call to register a new user
   */
  registerUser(){
    console.log("Register new user...");
    this.authService.registerUser(this.newUser).subscribe((data) => {
      console.log('Registered user: ', data);
      let message = `User ${this.newUser.userId} is registered successfully`;
      if(data){
        this.matSnackbar.open(message, '', {
          duration: 2000
        });
      } 
      this.router.navigate(['/login']);
    })
  }
  /**
   * Reset form input
   */
  resetInput(){
    console.log("Reset register form input!");
  }

}
