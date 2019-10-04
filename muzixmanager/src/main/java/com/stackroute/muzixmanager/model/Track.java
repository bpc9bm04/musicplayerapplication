package com.stackroute.muzixmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model class for music track
 * @author VijayBhusan.Kumar@cognizant.com
 */
@Entity
@Table(name="TRACK")
public class Track {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int trackId;
	
	@Column(name="TRACK_ID")
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ARTIST_NAME")
	private String artistName;
	
	@Column(name="ALBUM_NAME")
	private String albumName;
		
	@Column(name="HREF")
	private String href;	
	
	@Column(name="PREVIEW_URl")
	private String previewURL;
		
	@Column(name="PLAYBACK_SECONDS")
	private int playbackSeconds;
		
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="BOOKMARKED", columnDefinition = "TINYINT(1)")
	private boolean bookmarked;
	
	public Track() {}
	
	public Track(int id, String trackId, String name, String artistName, String albumName, String href, String previewURL, boolean bookmarked,
			int playbackSeconds, String userId) {		
		this.trackId = id;
		this.id = trackId;
		this.name = name;
		this.artistName = artistName;
		this.albumName = albumName;
		this.href = href;
		this.previewURL = previewURL;
		this.bookmarked = bookmarked;		
		this.playbackSeconds = playbackSeconds;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTrackId() {
		return trackId;
	}

	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getPreviewURL() {
		return previewURL;
	}

	public void setPreviewURL(String previewURL) {
		this.previewURL = previewURL;
	}
	
	public int getPlaybackSeconds() {
		return playbackSeconds;
	}

	public void setPlaybackSeconds(int playbackSeconds) {
		this.playbackSeconds = playbackSeconds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isBookmarked() {
		return bookmarked;
	}

	public void setBookmarked(boolean bookmarked) {
		this.bookmarked = bookmarked;
	}	
}
