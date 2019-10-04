import { Component, OnInit } from '@angular/core';
import { Track } from '../../track';
import { TrackService } from '../../track.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HttpHandlerService } from '../../httphandler.service';

@Component({
  selector: 'bookmark-component',
  templateUrl: './bookmark.component.html',
  styleUrls: ['./bookmark.component.css']
})
export class BookmarkComponent implements OnInit {

  tracks: Array<Track>;
  usePlaylistApi: boolean;
  errorResponse: HttpErrorResponse;
  tracksResponse: Track;

  constructor(private trackService: TrackService, private httpHandlerService: HttpHandlerService, private matSnackbar: MatSnackBar) { 
    this.tracks = [];
    this.usePlaylistApi = true;    
  }

  /**
   * Fetch the traks to be displayed on bookmarks page
   */
  ngOnInit() {
    let message = "No track bookmarked yet!";
    this.trackService.getPlaylistTracks().subscribe((tracks) => {     
      //shows only tracks which are bookmarked
     this.tracks = tracks.filter(track => track.bookmarked == true);  
      if(this.tracks.length === 0){
        this.matSnackbar.open(message, '', {
          duration: 2000
        });
      }      
    });
  }

 /**
   * Move the track to playlist and handle the HttpResponse from backend
   * @param track 
   */
  moveTrackToPlaylist(track){   
    console.log("Move bookmarked track to playlist...", `${track.id}`);   
    console.log("Track bookmarked :" , track.bookmarked=false, track);
    let index = this.tracks.indexOf(track);   
    this.trackService.bookmarkTrack(track).subscribe(response => {  
      this.tracks.splice(index, 1);
      console.log("Response: ", response);         
      this.tracksResponse = response.body;      
      let message = `'${this.tracksResponse.name}' moved to playlist`;
      this.httpHandlerService.handleResponse(response, message);
    },
    error => { // error path
      console.log("Error: ", error);
      this.errorResponse = error; 
      this.httpHandlerService.handleError(this.errorResponse);
    });     
  }
}
