import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TrackService } from '../../track.service';
import { Track } from '../../track';

@Component({
  selector: 'track-thumbnail',
  templateUrl: './thumbnail.component.html',  
  styleUrls: ['./thumbnail.component.css']
})
export class ThumbnailComponent {

  @Input()
  track: Track;  
  @Input()
  usePlaylistApi: boolean;
  @Output()
  addTrack = new EventEmitter();
  @Output()
  deleteTrack = new EventEmitter();
  @Output()
  bookmarkTrack = new EventEmitter();

  constructor(private trackService: TrackService) {    
   }

  /**
   * Add the track to my playlist
   */
  addToPlaylist(){     
    this.addTrack.emit(this.track);
  }

/**
 * Delete track from my playlisyt
 */
  deleteFromPlaylist(){    
    this.deleteTrack.emit(this.track);
  }
  
  /**
   * Bookmark the track
   */
  bookmarkTheTrack(){
    this.bookmarkTrack.emit(this.track);
  }
}

