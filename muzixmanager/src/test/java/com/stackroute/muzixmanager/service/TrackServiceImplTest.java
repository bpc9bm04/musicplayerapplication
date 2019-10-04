package com.stackroute.muzixmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.muzixmanager.exception.TrackExistException;
import com.stackroute.muzixmanager.exception.TrackNotFoundException;
import com.stackroute.muzixmanager.model.Track;
import com.stackroute.muzixmanager.repository.TrackRepository;

public class TrackServiceImplTest {
	
	/**
	 * Mock TrackRepository
	 */
	@Mock
	private transient TrackRepository trackRepo;
	private Track track;
	
	/**
	 * Inject TrackServiceImpl
	 */
	@InjectMocks
	private transient TrackServiceImpl trackServiceImpl;
	transient Optional<Track> trackOptions;
	
	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		track = new Track(1, "Tra.309", "track1", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd.png", true, 85, "u101");
		trackOptions = Optional.of(track);
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull("Jpa creations fail: use @injectMock on ServiceTrackImpl, track");
	}
	
	/**
	 * Testing saveTrack method happy path
	 * @throws TrackExistException
	 */
	@Test
	public void testSaveTrack_success() throws TrackExistException {
		when(trackRepo.save(track)).thenReturn(track);
		final boolean flag = trackServiceImpl.saveTrack(track);
		assertTrue("Saving track failed, the call to ServiceTrackImpl failed", flag);
		verify(trackRepo, times(1)).save(track);
		verify(trackRepo, times(1)).findByTrackId(track.getTrackId());
	}
	
	/**
	 * Testing saveTrack method failure case
	 * @throws TrackExistException
	 */
	@Test(expected = TrackExistException.class)	
	public void saveTrack_failure() throws TrackExistException {
		when(trackRepo.findByTrackId(1)).thenReturn(trackOptions);
		when(trackRepo.save(track)).thenReturn(track);
		final boolean flag = trackServiceImpl.saveTrack(track);
		assertFalse("Saving track failed", flag);
		verify(trackRepo, times(1)).findByTrackId(track.getTrackId());
	}
	
	/**
	 * Testing bookmarkTrack method success case
	 * @throws TrackNotFoundException
	 */
	@Test
	public void testBookmarkTrack_success() throws TrackNotFoundException {
		when(trackRepo.findByTrackId(1)).thenReturn(trackOptions);
		when(trackRepo.save(track)).thenReturn(track);
		track.setBookmarked(false);
		Track bookmarkedTrack = trackServiceImpl.bookmarkTrack(1, track);
		assertNotNull(bookmarkedTrack);
		assertEquals(track.isBookmarked(), bookmarkedTrack.isBookmarked());
		assertEquals("Saving track failed", false, track.isBookmarked());
		verify(trackRepo, times(1)).findByTrackId(track.getTrackId());
	}
	
	/**
	 * Testing bookmarkTrack method failure case
	 * @throws TrackNotFoundException
	 */
	@Test(expected = TrackNotFoundException.class)
	public void testBookmarkTrack_failure() throws TrackNotFoundException {	
		when(trackRepo.findByTrackId(3)).thenReturn(Optional.empty());			
		trackServiceImpl.bookmarkTrack(3, track);	
		verify(trackRepo, times(1)).findByTrackId(track.getTrackId());
	}
	
	/**
	 * Testing deleteTrack method success case
	 * @throws TrackNotFoundException
	 */
	@Test
	public void testDeleteTrack_success() throws TrackNotFoundException {
		when(trackRepo.findByTrackId(1)).thenReturn(trackOptions);
		doNothing().when(trackRepo).delete(track);
		final boolean flag = trackServiceImpl.deleteTrack(1);
		assertTrue("Deleting track failed", flag);
		verify(trackRepo, times(1)).delete(track);
		verify(trackRepo, times(1)).findByTrackId(track.getTrackId());
	}	
	
	/**
	 * Testing deleteTrack method failure case
	 * @throws TrackNotFoundException
	 */
	@Test(expected = TrackNotFoundException.class)	
	public void testDeleteTrack_failure() throws TrackNotFoundException {
		when(trackRepo.findByTrackId(3)).thenReturn(Optional.empty());			
		trackServiceImpl.deleteTrack(3);
		verify(trackRepo, times(1)).findByTrackId(track.getTrackId());
	}

	/**
	 * Testing findTrackById method success case
	 * @throws TrackNotFoundException
	 */
	@Test
	public void testFindTrackById_success() throws TrackNotFoundException {
		when(trackRepo.findByTrackId(1)).thenReturn(trackOptions);		
		final Track actualTrack = trackServiceImpl.findTrackById(1);
		assertEquals("Finding track by id success", trackOptions.get(), actualTrack);
		verify(trackRepo, times(1)).findByTrackId(track.getTrackId());
	}
	
	/**
	 * Testing findTrackById method failure case
	 * @throws TrackNotFoundException
	 */
	@Test(expected = TrackNotFoundException.class)
	public void testFindTrackById_failure() throws TrackNotFoundException {
		when(trackRepo.findByTrackId(3)).thenReturn(Optional.empty());		
		final Track actualTrack = trackServiceImpl.findTrackById(3);
		assertEquals("Finding track by id failed", track, actualTrack);
		verify(trackRepo, times(1)).findByTrackId(track.getTrackId());
	}
	

	/**
	 * Testing findByTrackName method success case
	 * @throws TrackNotFoundException 
	 * 
	 */
	@Test
	public void testFindTrackByName_success() throws TrackNotFoundException {
		final String name = "track1";
		when(trackRepo.findByName(name)).thenReturn(track);		
		final Track actualTrack = trackServiceImpl.findTrackByName(name);
		assertEquals("Finding track by name success", track, actualTrack);
		verify(trackRepo, times(1)).findByName(track.getName());
	}
	
	/**
	 * Testing findTrackByName method failure case
	 * @throws TrackNotFoundException
	 */
	@Test(expected = TrackNotFoundException.class)
	public void testFindTrackByName_failure() throws TrackNotFoundException {
		final String name = "track1";
		when(trackRepo.findByName(name)).thenReturn(null);		
		final Track actualTrack = trackServiceImpl.findTrackByName(name);
		assertEquals("Finding track by name success", track, actualTrack);
		verify(trackRepo, times(1)).findByName(track.getName());
	}
	
	
	/**
	 * Test listAllTracks method
	 */
	@Test
	public void testListAllTracks() {
		final List<Track> trackList = new ArrayList<>(1);
		when(trackRepo.findAll()).thenReturn(trackList);
		final List<Track> actualTrackList = trackServiceImpl.listAllTracks();
		assertEquals(trackList, actualTrackList);
		verify(trackRepo, times(1)).findAll();
	}
	
	/**
	 * Test getUserTracks method
	 */
	@Test
	public void testGetUserTracks() {
		final List<Track> trackList = new ArrayList<>(1);
		final String userId = "u101";
		trackList.add(track);
		when(trackRepo.findByUserId("u101")).thenReturn(trackList);
		final List<Track> actualTrackList = trackServiceImpl.getUserTracks(userId);
		assertEquals(trackList, actualTrackList);
		verify(trackRepo, times(1)).findByUserId(userId);
	}
}
