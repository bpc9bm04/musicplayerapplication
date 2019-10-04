package com.stackroute.muzixmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.muzixmanager.model.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
	
	Track findByName(String name);
	List<Track> findByUserId(String userId);
	Optional<Track> findByTrackId(int trackId);
}
