import { Component, OnInit, Input, Output } from '@angular/core';
import { Playlist } from '../../playlist';
import { PlaylistService } from '../../playlist.service'
import { ActivatedRoute} from '@angular/router'
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { TrackService } from '../../track.service';
import { Track } from '../../track';
import { MatDialog } from '@angular/material/dialog';
import { PlaylistDialogComponent } from '../playlist-dialog/playlist-dialog.component';

@Component({
  selector: 'playlist-container',
  templateUrl:'./playlist-container.component.html',
  styleUrls: ['./playlist-container.component.css']
})
export class PlaylistContainerComponent implements OnInit {

  playlists: Array<Playlist>;
  playlistType: string;  
  tracks: Array<Track>;
  name: string;
  
  constructor(private route: ActivatedRoute, private playlistService: PlaylistService, private trackService: TrackService, private snackBar: MatSnackBar, public dialog: MatDialog) {
    this.playlists = [];
    this.tracks = [];
  }
  
  ngOnInit() {   
    console.log(this.route);
      this.route.data.subscribe(datum => {
        console.log(datum)
        this.playlistType = datum.playlistType;
      }) 
      this.playlistService.getPlaylists(this.playlistType).subscribe((playlists) => {                      
      this.playlists = playlists;      
    });
  } 


  /**
   * Show all the tracks from a particular playlist in a dialog box
   * @param playlist
   */
  showTrackFromPlaylist(playlist){   
    console.log("Playlist..." , playlist.name);
    this.name = playlist.name;
    this.trackService.getTracksByPlaylistId(playlist.id).subscribe(tracks => {  
    this.tracks = tracks;
    this.openDialog();        
    });  
    
  }

  /**
   * Opena dialog box to show all the tracks
   */
  openDialog(){
    console.log("open dialog");
    
    let dialogRef = this.dialog.open(PlaylistDialogComponent, {
      width :'910px',
      height: '700px',
      data: { obj: {tracks: this.tracks, name : this.name} }
    });
    
    dialogRef.afterClosed().subscribe(result => {
      console.log('close dialog');
    });
  }
}
