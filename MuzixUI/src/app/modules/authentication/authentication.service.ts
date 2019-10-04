import { Injectable } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as jwt_decode from 'jwt-decode';

export const TOKEN_NAME:string = 'jwt_token';

@Injectable()
export class AuthenticationService {

  springEndpoint:string;
  token:string;

  constructor(private httpClient:HttpClient){
    this.springEndpoint = 'http://localhost:8181/accountmanager';
  }

  /**
   * Register a new user
   * 
   * @param newUser 
   */
  registerUser(newUser) {
    const url = this.springEndpoint + "/register";
    console.log('Register newUser', newUser);
    return this.httpClient.post(url, newUser, {responseType: 'text'});
  }

  /**
   * Login the user to the application
   * @param user
   */
  loginUser(user) {
    const url = this.springEndpoint + "/login";
    console.log('Login user', user);
    return this.httpClient.post(url, user);
  }

  /**
   * Set the token to the request header/local storage of the browser
   * @param token 
   */
  setToken(token:string) {
    return localStorage.setItem(TOKEN_NAME, token);
  }

  /**
   * Get the token from the local storage of the browser
   */
  getToken(){
    return localStorage.getItem(TOKEN_NAME);
  }

  /**
   * Delete the token from the local storage of the browser
   */
  deleteToken(){
    return localStorage.removeItem(TOKEN_NAME);
  }

  /**
   * 
   * @param token 
   */
  getTokenExpirationDate(token:string): Date {
    const decoded:any  = jwt_decode(token);
    if(decoded.exp === undefined) return null;
    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }
  
  /**
   * Check if the token is expired 
   * @param token 
   */
  isTokenExpired(token?:string):boolean {
    if(!token) token = this.getToken();
    if(!token) return true;
    const date = this.getTokenExpirationDate(token);
    if(date === undefined || date === null) return false;
    return !(date.valueOf() > new Date().valueOf());
  }

 }
