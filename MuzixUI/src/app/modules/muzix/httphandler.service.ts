import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class HttpHandlerService {

constructor(private httpClient: HttpClient, private matSnackbar: MatSnackBar) { }

/**
 * Method to handle the http response object
 * @param response the response object returned from backend
 * @param message the custom message to display on the UI
 */
  public handleResponse(response: HttpResponse<Object>, message: string){
    console.log("Response body: ", response.body);
    console.log("Message: ", message);
    if(response.status == 200){      
      this.matSnackbar.open(message, '', {
        duration: 1500
      });
    }
    if(response.status == 201){
      this.matSnackbar.open(message, '', {
        duration: 1500
      });
    }    
  }      

  /**
   * Method to handle the http error response
   * @param error the error response object returned from backend
   */
  public handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      let message = `${error.error.message}`;     
      this.matSnackbar.open(message, '', {
            duration: 1500
      });
      console.error(`Error [code]: ${error.status}, ` +  `[message]: ${error.error.message}`);      
    }    
  }
}