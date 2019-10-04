package com.stackroute.muzixmanager.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.muzixmanager.exception.TrackExistException;
import com.stackroute.muzixmanager.exception.TrackNotFoundException;
import com.stackroute.muzixmanager.model.Track;
import com.stackroute.muzixmanager.repository.TrackRepository;

@Service
public class TrackServiceImpl implements TrackService {
	
	@Autowired
	TrackRepository trackRepository;

	/**
	 * Save new track to the repository
	 */
	@Override
	public boolean saveTrack(final Track track) throws TrackExistException {
		final Optional<Track> trackOpt = trackRepository.findByTrackId(track.getTrackId());		
		if(trackOpt.isPresent()) {
			throw new TrackExistException("Track already exist, can't save!");
		}
		trackRepository.save(track);
		return true;
	}


	/**
	 * Bookmark track into the repository
	 */
	@Override
	public Track bookmarkTrack(int id, final Track bookMarkedTrack) throws TrackNotFoundException {
		
		Optional<Track> trackOpt = trackRepository.findByTrackId(id); 
		if(!trackOpt.isPresent()) {
			throw new TrackNotFoundException("Track doesn't exist, can't bookmark!");
		}
		trackOpt.get().setBookmarked(bookMarkedTrack.isBookmarked());
		trackRepository.save(trackOpt.get());		
		return trackOpt.get();
	}
	
	
	/**
	 * Delete track from the repository
	 */
	@Override
	public boolean deleteTrack(int id) throws TrackNotFoundException {
		Optional<Track> trackOpt = trackRepository.findByTrackId(id); 
		if(!trackOpt.isPresent()) {
			throw new TrackNotFoundException("Track doesn't exist, can't delete!");
		}
		trackRepository.delete(trackOpt.get());
		return true;
	}

	/**
	 * Find track by Id
	 */
	@Override
	public Track findTrackById(int id) throws TrackNotFoundException {
		Optional<Track> trackOpt = trackRepository.findByTrackId(id); 
		if(!trackOpt.isPresent()) {
			throw new TrackNotFoundException("Track doesn't exist, couldn't found!");
		}		
		return trackOpt.get();
	}

	/**
	 * Find track by name
	 */
	@Override
	public Track findTrackByName(String name) throws TrackNotFoundException {		
		Track track = trackRepository.findByName(name);
		if(Objects.isNull(track)) {
			throw new TrackNotFoundException("Track doesn't exist, couldn't found!");
		}
		return track;
	}	

	/**
	 * List all the tracks added by user
	 */
	@Override
	public List<Track> getUserTracks(String userId) {
		return trackRepository.findByUserId(userId);
	}
	
	/**
	 * List all the tracks
	 */
	@Override
	public List<Track> listAllTracks() {
		return trackRepository.findAll();
	}
}


