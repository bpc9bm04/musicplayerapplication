import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Track } from '../../track';
import { TrackService } from '../../track.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HttpHandlerService } from '../../httphandler.service';

@Component({
  selector: 'playlist-dialog',
  templateUrl: './playlist-dialog.component.html',
  styles: ['./playlist-dialog.component.css']
})
export class PlaylistDialogComponent {
    
  tracksResponse: Track; 
  errorResponse: HttpErrorResponse; 

  constructor(private snackBar: MatSnackBar, private dialogRef: MatDialogRef<PlaylistDialogComponent>, 
  @Inject(MAT_DIALOG_DATA) public data: any, private trackService: TrackService, private httpHandlerService: HttpHandlerService,) { }

  /**
   * Click to close dialog box
   */
  onCloseClick(){
    this.dialogRef.close();
  } 

  /**
   * Add the track to my playlist and handle the HttpResponse from backend
   * @param track 
   */
  addTrackToPlaylist(track){   
    console.log("Add track to playlist called..." , `${track.id}`)   
    this.trackService.saveTrackToPlaylist(track).subscribe(response => {  
      console.log("Response: ", response);         
      this.tracksResponse = response.body;      
      let message = `'${this.tracksResponse.name}' added to playlist`;
      this.httpHandlerService.handleResponse(response, message);
    },
    error => { // error path
      console.log("Error: ", error);
      this.errorResponse = error; 
      this.httpHandlerService.handleError(this.errorResponse);
    });     
  }
  
}
