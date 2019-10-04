import { Component, OnInit, Input, Output } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HttpHandlerService } from '../../httphandler.service';
import { TrackService } from '../../track.service'
import { Track } from '../../track';

@Component({
  selector: 'track-container',
  templateUrl:'./track-container.component.html',
  styleUrls: ['./track-container.component.css']
})
export class TrackContainerComponent implements OnInit {
  
  @Input()
  track: Track;
  @Input()  
  tracks: Array<Track>;
  @Input()
  usePlaylistApi: boolean;
  errorResponse: HttpErrorResponse;
  tracksResponse: Track;
    
  constructor(private trackService: TrackService, private httpHandlerService: HttpHandlerService) {
  }
  ngOnInit() {    
  }

  /**
   * Add the track to playlist and handle the HttpResponse from backend
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

  /**
   * Delete the track from playlist and handle the HttpResponse from backend
   * @param movie 
   */
  deleteTrackFromPlaylist(track){  
    console.log("Delete track from playlist called...")     
    let message = `'${track.name}' deleted from playlist`;    
    let index = this.tracks.indexOf(track);  
    
    this.trackService.deleteTrackFromPlaylist(track).subscribe(response => {  
      this.tracks.splice(index, 1);
      console.log("Response: ", response);                    
      this.httpHandlerService.handleResponse(response, message);
    },
    error => { // error path
       console.log("Error: ", error);
       this.errorResponse = error; 
       this.httpHandlerService.handleError(this.errorResponse);
      } 
    );    
  }

  /**
   * Bookmark the track and remove from playlist
   */
  bookmarkTheTrack(track){  
    console.log("Bookmark track called for...", `${track.id}`);   
    console.log("Track bookmarked :" , track.bookmarked=true, track);
    let index = this.tracks.indexOf(track);    
    this.trackService.bookmarkTrack(track).subscribe(response => {  
      this.tracks.splice(index, 1);
      console.log("Response: ", response);         
      this.tracksResponse = response.body;      
      let message = `Track '${this.tracksResponse.name}' bookmarked`;
      this.httpHandlerService.handleResponse(response, message);
    },
    error => { // error path
      this.errorResponse = error; 
      this.httpHandlerService.handleError(this.errorResponse);
    });    
  }
}
