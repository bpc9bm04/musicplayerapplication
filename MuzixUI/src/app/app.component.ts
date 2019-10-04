import { Component } from '@angular/core';
import { AuthenticationService } from './modules/authentication/authentication.service';
import { Router } from '@angular/router';
import { OnInit, AfterViewChecked } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: []
})
export class AppComponent implements OnInit, AfterViewChecked {
  title = 'Welcome to Muzix App';
  showLogout: boolean;

  constructor(private authService:AuthenticationService, private router:Router) { }

  ngOnInit(){   
     this.showLogout = false;        
  }

  /**
   * Show logout if token is present
   */
  ngAfterViewChecked(){    
    let flag = this.authService.isTokenExpired();
   if(!flag){
     this.showLogout = true;    
   }
  }

  /**
   * Logged out the user from the application and delete the token
   */
  logout() {
    console.log('Logged out!');
    this.authService.deleteToken();
    this.showLogout = false;
    console.log('Token deleted');
    this.router.navigate(['/login']);
  }
}
