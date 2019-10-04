package com.stackroute.muzixmanager.service;

import java.util.List;

import com.stackroute.muzixmanager.exception.TrackExistException;
import com.stackroute.muzixmanager.exception.TrackNotFoundException;
import com.stackroute.muzixmanager.model.Track;


public interface TrackService {
	/**
	 * Save the track
	 * @param track
	 * @return boolean
	 * @throws TrackExistException
	 */
	boolean saveTrack(Track track) throws TrackExistException;
	
	/**
	 * Delete a track
	 * @param id
	 * @return boolean
	 * @throws TrackNotFoundException
	 */
	boolean deleteTrack(int id) throws TrackNotFoundException;	

	/**
	 * Bookmark a track
	 * @param id 
	 * @param track
	 * @return Track
	 * @throws TrackNotFoundException
	 */
	Track bookmarkTrack(int id, Track track) throws TrackNotFoundException;
	
	/**
	 * Find a track by Id
	 * @param id
	 * @return Track
	 * @throws TrackNotFoundException
	 */
	Track findTrackById(int id) throws TrackNotFoundException;
	
	/**
	 * Find a track by name
	 * @param name
	 * @return Track
	 * @throws TrackNotFoundException
	 */
	Track findTrackByName(String name) throws TrackNotFoundException;
	
	/**
	 * List all the tracks
	 * @return List of Track
	 */
	List<Track> listAllTracks();
	

	/**
	 * Get all the tracks saved by the user
	 * @param userId
	 * @return List of Movie
	 */
	List<Track> getUserTracks(String userId);
}
