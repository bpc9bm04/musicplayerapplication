import { Component, OnInit } from '@angular/core';
import { Track } from '../../track';
import { TrackService } from '../../track.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'track-playlist',
  templateUrl: './playlist.component.html',
  styles: []
})
export class PlaylistComponent implements OnInit {

  tracks: Array<Track>;
  usePlaylistApi: boolean;

  constructor(private trackService: TrackService, private matSnackbar: MatSnackBar) { 
    this.tracks = [];
    this.usePlaylistApi = true;    
  }

  /**
   * Fetch the tracks to be displayed on my playlist page
   */
  ngOnInit() {
    let message = "Playlist is empty!";
    this.trackService.getPlaylistTracks().subscribe((tracks) => {
      //filter out the bookmarked tracks
      this.tracks = tracks.filter(track => track.bookmarked == false); 
      if(this.tracks.length === 0){
        this.matSnackbar.open(message, '', {
          duration: 2000
        });
      }          
    });
  }
}
