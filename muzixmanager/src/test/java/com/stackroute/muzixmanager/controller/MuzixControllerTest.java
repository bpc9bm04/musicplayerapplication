package com.stackroute.muzixmanager.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.muzixmanager.constant.AuthConstant;
import com.stackroute.muzixmanager.model.Track;
import com.stackroute.muzixmanager.service.TrackService;

@RunWith(SpringRunner.class)
@WebMvcTest(MuzixController.class)
public class MuzixControllerTest {

	/**
	 * Reference to MockMvc 
	 */
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TrackService trackService;
	private Track track;
	static List<Track> tracks; 
	@InjectMocks
	private MuzixController muzixController;
	
	private static final String USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1MTAxIiwiaWF0IjoxNTUxOTY4ODc4fQ.TmZMWxOAK6L3-xmoHNpJNr82PAsERXmQbDlnvETW2-A";
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(muzixController).build();
		tracks = new ArrayList<>();
		track = new Track(1, "Tra.309", "track1", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd.png", true, 85, "u101");
		tracks.add(track);
		track = new Track(2, "Tra.309", "track2", "t-series", "Happy Day", "/ubuntu/Desktop/MuzixApp/location", "hd2.png", true, 105, "u102");
		tracks.add(track);		
	}
	
	/**
	 * to test save track
	 * @throws Exception
	 */
	@Test
	public void testSaveNewTrack_success() throws Exception {		
		when(trackService.saveTrack(track)).thenReturn(true);
		mockMvc.perform(post("/muzixmanager/track").header(AuthConstant.AUTHORIZATION, AuthConstant.BEARER + USER_TOKEN)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(jsonToString(track))).andExpect(status().isCreated()).andDo(print());
		verify(trackService, times(1)).saveTrack(Mockito.any(Track.class));
		verifyNoMoreInteractions(trackService);
	}
	
	/**
	 * to test update track
	 * @throws Exception
	 */
	@Test
	public void testBookmarkTrack_success() throws Exception {		
		track.setBookmarked(false);
		when(trackService.bookmarkTrack(2, track)).thenReturn(track);
		mockMvc.perform(put("/muzixmanager/track/{id}", 2).contentType(MediaType.APPLICATION_JSON).content(jsonToString(track))).andExpect(status().isOk());
		verify(trackService, times(1)).bookmarkTrack(Mockito.eq(2), Mockito.any(Track.class));
		verifyNoMoreInteractions(trackService);
	}
	
	/**
	 * to test delete track
	 * @throws Exception
	 */
	@Test
	public void testDeleteTrack_success() throws Exception {		
		when(trackService.deleteTrack(1)).thenReturn(true);
		mockMvc.perform(delete("/muzixmanager/track/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(trackService, times(1)).deleteTrack(1);
		verifyNoMoreInteractions(trackService);
	}
	
	/**
	 * to test find track by id
	 * @throws Exception
	 */
	@Test
	public void testFindTrackById_success() throws Exception {
		when(trackService.findTrackById(1)).thenReturn(tracks.get(0));
		mockMvc.perform(get("/muzixmanager/track/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(trackService, times(1)).findTrackById(1);
		verifyNoMoreInteractions(trackService);
	}
	
	/**
	 * to test find track by name
	 * @throws Exception
	 */
	@Test
	public void testFindTrackByName_success() throws Exception {
		final String name = "track1";
		when(trackService.findTrackByName(name)).thenReturn(tracks.get(0));
		mockMvc.perform(get("/muzixmanager/track/name/{name}", name).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(trackService, times(1)).findTrackByName(name);
		verifyNoMoreInteractions(trackService);
	}
	
	/**
	 * to test list all tracks in DB
	 * @throws Exception
	 */
	@Test
	public void testFetchAllTracks_success() throws Exception {
		when(trackService.listAllTracks()).thenReturn(tracks);
		mockMvc.perform(get("/muzixmanager/alltracks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(trackService, times(1)).listAllTracks();
		verifyNoMoreInteractions(trackService);
	}
	
	/**
	 * to test list user tracks
	 * @throws Exception
	 */
	@Test
	public void testFetchUserTracks_success() throws Exception {
		final String userId = "u101";		
		when(trackService.getUserTracks(userId)).thenReturn(tracks);
		mockMvc.perform(get("/muzixmanager/tracks").header(AuthConstant.AUTHORIZATION, AuthConstant.BEARER + USER_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(trackService, times(1)).getUserTracks(userId);
		verifyNoMoreInteractions(trackService);
	}
	
	/**
	 * Parse string format object to json format
	 * @param object
	 * @return
	 * @throws JsonProcessingException
	 */
	private static String jsonToString(final Object object) throws JsonProcessingException {
		
		final ObjectMapper objMapper = new ObjectMapper();
		String stringValue;
		try {
			final String jsonValue = objMapper.writeValueAsString(object);
			stringValue = jsonValue;
		} catch (JsonProcessingException e) {
			stringValue = "Json processing exception";			
		}
		return stringValue;
	}
}
