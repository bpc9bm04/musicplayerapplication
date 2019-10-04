import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { AuthenticationService } from './authentication.service';
import { FormsModule } from '@angular/forms';
import { AuthenticationRoutingModule } from './authentication-router.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  imports: [CommonModule, FormsModule, AuthenticationRoutingModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, MatCardModule],
  declarations: [RegisterComponent, LoginComponent],
  providers: [ AuthenticationService],
  exports: [RegisterComponent, LoginComponent]
})
export class AuthenticationModule { }
