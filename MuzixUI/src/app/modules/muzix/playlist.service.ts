import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators/map';
import { retry } from 'rxjs/operators';
import { Playlist } from './playlist';
import { Track } from './track';

@Injectable()
export class PlaylistService {

  playlistEndpoint: string;
  apikey: string;  
  imageEndpoint: string;
  tracksEndpoint: string;

  constructor(private httpClient: HttpClient) {
    this.playlistEndpoint = 'https://api.napster.com/v2.1/playlists';
    this.tracksEndpoint =' https://api.napster.com/v2.1/playlists/pp.253600731/tracks'
    this.imageEndpoint = 'https://direct.napster.com/imageserver/v2/playlists';
    this.apikey = "apikey=Y2ZmMWEzMDMtYjNhNi00YmNhLWI3MjItNzJiYzMyZGQzNjM0";     
  }

  /**
   * Get and list all the playlist of specific type
   * @param type type of the playlist e.g. featured 
   */
  getPlaylists(type: string): Observable<Array<Playlist>> {
   
    const endPointUrl = `${this.playlistEndpoint}/${type}?${this.apikey}`;
    console.log("Fetch playlist URL :", endPointUrl);
    
    return this.httpClient.get(endPointUrl).pipe(
      retry(3),
      map(this.pickTrackResults),
      map(this.transformImagePath.bind(this))
    );
  }

  /**
   * set the image path in the playlist object for each playlist in the list
   * @param playlists Array<Playlist>
   */
  transformImagePath(playlists): Array<Playlist> {    
    return playlists.map(playlist => {
      playlist.url = `${this.imageEndpoint}/${playlist.id}/artists/images/230x153.jpg`;
      playlist.href = `${this.playlistEndpoint}/${playlist.id}/tracks?${this.apikey}&limit=9`;
      return playlist;
    });
  }

  /**
   * Returns the playlists from the response object
   * @param response HttpResponse
   */
  pickTrackResults(response) {
    console.log("Playlists: ", response.playlists);
    return response['playlists'];
  }
}
