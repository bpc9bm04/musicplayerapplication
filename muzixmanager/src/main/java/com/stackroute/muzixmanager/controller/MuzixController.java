package com.stackroute.muzixmanager.controller;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.muzixmanager.constant.AuthConstant;
import com.stackroute.muzixmanager.exception.TrackExistException;
import com.stackroute.muzixmanager.exception.TrackNotFoundException;
import com.stackroute.muzixmanager.model.Track;
import com.stackroute.muzixmanager.service.TrackService;

import io.jsonwebtoken.Jwts;

/**
 * Controller class to control the request mapping flow
 * @RequestMapping used to route the http request which has the format "/muzixmanager" 
 * @author VijayBhusan.Kumar
 *
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/muzixmanager")
public class MuzixController {
	
	@Autowired
	private TrackService trackService;
	
	/**
	 * Save new track
	 * @param track
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/track")
	public ResponseEntity<?> saveTrack(@RequestBody final Track track, HttpServletRequest request, HttpServletRequest response) {
		System.out.println("Save track... " + track.getName());
		ResponseEntity<?> responseEntity;
		String userId = filterUserIdFromRequest(request);
		
		try {
			track.setUserId(userId);
			trackService.saveTrack(track);
			responseEntity = new ResponseEntity<Track>(track, HttpStatus.CREATED);
		} catch (TrackExistException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	
	/**
	 * Bookmark a track
	 * @param id
	 * @param track
	 * @return {@link ResponseEntity}
	 */
	@PutMapping(path = "/track/{id}")
	public ResponseEntity<?> bookmarkTrack(@PathVariable("id") final int id, @RequestBody final Track track){
		ResponseEntity<?> responseEntity;
		Track bookmarkedTrack = null;
		try {
			bookmarkedTrack = trackService.bookmarkTrack(id, track);			
		} catch (TrackNotFoundException me) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + me.getMessage() + "\"}", HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		responseEntity = new ResponseEntity<Track>(bookmarkedTrack, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * Delete a track
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping(path ="/track/{id}")
	public ResponseEntity<?> deleteTrack(@PathVariable("id") final int id){
		ResponseEntity<?> responseEntity;
		try {
			trackService.deleteTrack(id);			
		} catch (TrackNotFoundException me) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + me.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		responseEntity = new ResponseEntity<String>("{ \"message\" : \"Track deleted\"}", HttpStatus.OK);
		return responseEntity;
	} 
	
	/**
	 * Find a track by id
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	@GetMapping(path ="/track/{id}")
	public ResponseEntity<?> findTrackById(@PathVariable("id") final int id){
		ResponseEntity<?> responseEntity;
		Track track = null;
		try {
			 track = trackService.findTrackById(id);			 
		} catch (TrackNotFoundException me) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + me.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseEntity = new ResponseEntity<Track>(track, HttpStatus.OK);
		return responseEntity;
	} 

	/**
	 * List all the tracks
	 * @return list of Tracks
	 */
	@GetMapping("/alltracks")
	public ResponseEntity<List<Track>> fetchAllTracks(){
		return new ResponseEntity<List<Track>>(trackService.listAllTracks(), HttpStatus.OK);
	}
   
				
	/**
	 * Find a track by name
	 * @param name
	 * @return
	 */
	@GetMapping(path ="/track/name/{name}")
	public ResponseEntity<?> findTrackByName(@PathVariable("name") final String name){
		ResponseEntity<?> responseEntity;
		Track track = null;
		try {
			 track = trackService.findTrackByName(name);		
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		responseEntity = new ResponseEntity<Track>(track, HttpStatus.OK);
		return responseEntity;
	}
	
	
	/**
	 * Find all the tracks save by an user
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(path ="/tracks")
	public ResponseEntity<?> fetchUserTracks(final HttpServletRequest request, final HttpServletResponse response){
		String userId = filterUserIdFromRequest(request);
		return new ResponseEntity<List<Track>>(trackService.getUserTracks(userId), HttpStatus.OK);
	}	
	
	/**
	 * Filter out the userId from the request header
	 * @param request {@link ServletRequest}
	 * @return userId
	 */
	private String filterUserIdFromRequest(ServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authHeader = httpServletRequest.getHeader(AuthConstant.AUTHORIZATION);
		String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey(AuthConstant.BASE64_ENCODED_KEY).parseClaimsJws(token).getBody().getSubject();
		System.out.println("User Id: " + userId);
		return userId;
	}
}

