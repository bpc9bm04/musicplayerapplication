package com.stackroute.muzixmanager.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.muzixmanager.model.Track;

/**
 * Class to run TrackRespository
 * 
 * @author VijayBhusan.Kumar
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class TrackRepositoryTest {

	@Autowired
	private transient TrackRepository trackRepo;

	public void setTrackRepo(final TrackRepository trackRepo) {
		this.trackRepo = trackRepo;
	}

	@Test
	public void testSaveTrack() throws Exception {
		Track actualTrack = trackRepo.save(createTrack(1, "Tra.309", "track1", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd.png", true, 85, "u101"));
		final Track track = trackRepo.getOne(1);
		assertEquals(track.getTrackId(), actualTrack.getTrackId());
	}

	@Test
	public void testBookmarkTrack() throws Exception {
		trackRepo.save(createTrack(1, "Tra.309" ,"track1", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd.png", true, 85, "u101"));
		final Track track = trackRepo.getOne(1);
		track.setBookmarked(false);
		trackRepo.save(track);
		final Track bookmarkedTrack = trackRepo.getOne(1);
		assertEquals(false, bookmarkedTrack.isBookmarked());
	}

	@Test
	public void testDeleteTrack() throws Exception {
		Track actualTrack = trackRepo.save(createTrack(1, "Tra.309", "track1", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd.png", true, 85, "u101"));
		final Track track = trackRepo.getOne(1);
		assertEquals(track.getName(), actualTrack.getName());
		trackRepo.delete(track);
		assertEquals(Optional.empty(), trackRepo.findById(1));
	}

	@Test
	public void testFindTrack() {
		Track actualTrack = trackRepo.save(createTrack(1, "Tra.309", "track1", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd.png", true, 85, "u101"));
		final Track track = trackRepo.getOne(1);
		assertEquals(track.getName(), actualTrack.getName());
	}

	@Test
	public void testListAllTracks() {
		trackRepo.save(createTrack(1, "Tra.309", "track1", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd.png", true, 85, "u101"));
		trackRepo.save(createTrack(2, "Tra.310", "track2", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd2.png",	false, 105, "u102"));
		List<Track> tracks = trackRepo.findAll();
		assertEquals(tracks.get(0).getName(), "track1");
		assertEquals(tracks.get(1).getName(), "track2");
	}

	public void testGetUserTracks() {
		trackRepo.save(createTrack(1, "Tra.309", "track1", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd.png", true, 85, "u101"));
		trackRepo.save(createTrack(2, "Tra.310", "track2", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd2.png", false, 105, "u102"));
		List<Track> tracks = trackRepo.findByUserId("u101");
		assertEquals(tracks.get(0).getName(), "track1");
		assertNotEquals(tracks.get(1).getName(), "track2");
	}

	private Track createTrack(int id, String trackId, String name, String artistName, String albumName, String href, String previewURL,
			boolean isBookmarked, int playbackSeconds, String userId) {
		Track track = new Track();
		track.setTrackId(id);
		track.setId(trackId);
		track.setName(name);
		track.setArtistName(artistName);
		track.setAlbumName(albumName);
		track.setHref(href);
		track.setPreviewURL(previewURL);
		track.setBookmarked(isBookmarked);		
		track.setPlaybackSeconds(playbackSeconds);
		track.setUserId(userId);
		return track;
	}
}
