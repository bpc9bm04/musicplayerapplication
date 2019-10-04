import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators/map';
import { retry } from 'rxjs/operators';
import { Track } from './track';

@Injectable()
export class TrackService {

  trackEndpoint: string;
  apikey: string;
  playlistEndpoint: string;
  springEndpoint: string;
  searchEndpint: string;
  imageEndpoint: string;  

  constructor(private httpClient: HttpClient) {
    this.trackEndpoint = 'https://api.napster.com/v2.1/tracks';    
    this.springEndpoint = 'http://localhost:8080/muzixmanager/';
    this.searchEndpint = 'https://api.napster.com/v2.2/search?';
    this.imageEndpoint = 'https://direct.napster.com/imageserver/v2/albums';
    this.playlistEndpoint = 'https://api.napster.com/v2.2/playlists';    
    this.apikey = "apikey=Y2ZmMWEzMDMtYjNhNi00YmNhLWI3MjItNzJiYzMyZGQzNjM0";
  }
  
  /**
   * Get and list all the tracks of specific type
   * @param type type of the track e.g. top 
   */
  getTracks(type: string): Observable<Array<Track>> {
    const endPointUrl = `${this.trackEndpoint}/${type}?${this.apikey}&limit=8`;
    console.log("Fetch tracks URL :", endPointUrl);

    return this.httpClient.get(endPointUrl).pipe(
      retry(3),
      map(this.pickTrackResults),
      map(this.transformImagePath.bind(this))
    );
  }

  /**
   * Get and list all the tracks of specific playlist
   * @param id id of the playlist e.g. pp.123456789 
   */
  getTracksByPlaylistId(id: string): Observable<Array<Track>> {
    const endPointUrl = `${this.playlistEndpoint}/${id}/tracks?${this.apikey}&limit=9`;
    console.log("Tracks from playlist URL :", endPointUrl);

    return this.httpClient.get(endPointUrl).pipe(
      retry(3),
      map(this.pickTrackResults),
      map(this.transformSmallImagePath.bind(this))
    );
  }

  /**
   * set the image path in the track object for each track in the list
   * @param tracks Array<Track>
   */
  transformSmallImagePath(tracks): Array<Track> {
    return tracks.map(track => {
      track.href = `${this.imageEndpoint}/${track.albumId}/images/230x153.jpg`;      
      return track;
    });
  }

  /**
   * set the image path in the track object for each track in the list
   * @param tracks Array<Track>
   */
  transformImagePath(tracks): Array<Track> {
    return tracks.map(track => {
      track.href = `${this.imageEndpoint}/${track.albumId}/images/500x500.jpg`;
      return track;
    });
  }

  /**
   * Returns the tracks from the response object
   * @param response HttpResponse
   */
  pickTrackResults(response) {
    console.log("Tracks: ", response.tracks);
    return response['tracks'];
  }

  /**
   * Method to search a track from Napster
   * @param searchKey search string to search a Track
   */
  searchTracks(searchKey: string, type: string = 'track'): Observable<Array<Track>> {

    if (searchKey.length > 0) {
      const searchUrl = `${this.searchEndpint}${this.apikey}&type=${type}&query=${searchKey}`;

      console.log("Search URL: ", searchUrl);
      return this.httpClient.get(searchUrl).pipe(
        retry(3),
        map(this.pickSearchedTracksResults),
        map(this.transformImagePath.bind(this))
      );
    }
  }

  /**
   * Returns the tracks from the search response object
   * @param response HttpResponse
   */
  pickSearchedTracksResults(response) {
    console.log("Searched Tracks: ", response.search.data);
    return response.search.data.tracks;
  }

  /**
   * List all the tracks which are in the playlist from backend
   */
  getPlaylistTracks(): Observable<Array<Track>> {
    const url = `${this.springEndpoint}/tracks`;
    return this.httpClient.get<Array<Track>>(url);
  }

  /**
   * Add a track to playlist, eventually save it to the DB
   * @param track Track object
   */
  saveTrackToPlaylist(track): Observable<HttpResponse<Track>> {
    const url = `${this.springEndpoint}/track`;
    return this.httpClient.post<Track>(url, track, { observe: "response" });
  }

  /**
   * Delete a track from playlist, eventually from the DB
   * @param track Track object
   */
  deleteTrackFromPlaylist(track): Observable<HttpResponse<Object>> {
    console.log("Delete Track", track.id, track.trackId);
    console.log("isBookmarked Track", track.bookmarked);
    const url = `${this.springEndpoint}/track/${track.trackId}`;    
    return this.httpClient.delete(url, { observe: "response" });
  }

  /**
   * Bookmark a track, locally in the DB
   * @param track Track object
   */
  bookmarkTrack(track): Observable<HttpResponse<Track>> {
    const url = `${this.springEndpoint}/track/${track.trackId}`;    
    console.log("Track isBookmarked :" , track.bookmarked);    
    return this.httpClient.put<Track>(url, track, { observe: "response" });
  }
}
