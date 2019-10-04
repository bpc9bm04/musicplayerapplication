import { Component, OnInit } from '@angular/core';
import { Track } from '../../track';
import { TrackService } from '../../track.service';
import { ActivatedRoute} from '@angular/router';

@Component({
  selector: 'track-dashboard-container',
  template: `
    <track-container [tracks]= "tracks"></track-container>
  `,
  styles: []
})
export class DashboardContainerComponent implements OnInit {

  tracks: Array<Track>;
  trackType: string;

  constructor(private trackService: TrackService, private route: ActivatedRoute) {
    this.tracks = [];
   }

/**
 * Get the tracks from trackService and push to this.tracks
 */
  ngOnInit() {
      console.log(this.route);
      this.route.data.subscribe(datum => {
        this.trackType = datum.trackType;
      });
      this.trackService.getTracks(this.trackType).subscribe((tracks) => {        
      this.tracks.push(...tracks)
    });
  }

}
