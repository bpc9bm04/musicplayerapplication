import { Component, OnInit } from '@angular/core';
import { TrackService } from '../../track.service';
import { Track } from '../../track';

@Component({
  selector: 'track-search',
  templateUrl: './search.component.html',
  styles: [`
    .search-form{
      margin: 10px;
    }
    `
  ]
})
export class SearchComponent {

  tracks: Array<Track>;
  
  constructor(private trackService: TrackService) { }
  
  /**
   * Search all the tracks having name contains 'searchKey'
   * @param searchKey 
   */
  onEnter(searchKey){
    console.log('Search track called... to search:', searchKey);
    this.trackService.searchTracks(searchKey).subscribe(tracks => {
      this.tracks = tracks;
    })
  }
}
